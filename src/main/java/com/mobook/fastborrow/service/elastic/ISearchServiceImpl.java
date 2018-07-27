package com.mobook.fastborrow.service.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mobook.fastborrow.converter.BookMessage2SearchVOConverter;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.elastic.template.BookIndexKey;
import com.mobook.fastborrow.elastic.template.BookIndexMessage;
import com.mobook.fastborrow.elastic.template.BookIndexTemplate;
import com.mobook.fastborrow.elastic.template.BookSuggest;
import com.mobook.fastborrow.repository.BookMessageRepository;
import com.mobook.fastborrow.repository.BookWhereRespository;
import com.mobook.fastborrow.repository.TagRepository;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import com.mobook.fastborrow.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:01 2018\7\25 0025
 */
@Service
@Slf4j
public class ISearchServiceImpl implements ISearchService {

    private static final String INDEX_NAME = "mobook";

    private static final String INDEX_TYPE = "book";

    private static final String INDEX_TOPIC = "book_build";

    @Autowired
    private BookMessageRepository repository;

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics = INDEX_TOPIC)
    private void handleMessage(String content){
        try {
            BookIndexMessage message = objectMapper.readValue(content, BookIndexMessage.class);
            switch (message.getOperation()){
                case BookIndexMessage.INDEX:
                    this.createOrUpdateIndex(message);
                    break;
                case BookIndexMessage.REMOVE:
                    this.removeIndex(message);
                    break;
                default:
                    log.warn("Not support message content "+content);
                    break;
            }
        } catch (IOException e) {
            log.error("Cannot pares json for "+content,e);
        }
    }

    private void createOrUpdateIndex(BookIndexMessage message){
        String isbn = message.getIsbn();
        BookMessage bookMessage = repository.findById(isbn).get();
        if (bookMessage == null){
            log.error("Index book {} dose not exist!",isbn);
            this.index(isbn,message.getRetry()+1);
            return;
        }
        //属性复制
        BookIndexTemplate indexTemplate = new BookIndexTemplate();
        BeanUtils.copyProperties(bookMessage,indexTemplate);
        indexTemplate.setTag(tagRepository.findByTagNum(bookMessage.getTagNum()).getTagName());

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(BookIndexKey.ISBN,isbn));
        log.debug(requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();

        boolean success;
        long totalHit = searchResponse.getHits().getTotalHits();
        if (totalHit == 0){
            success = create(indexTemplate);
        }else if(totalHit == 1){
            String esId = searchResponse.getHits().getAt(0).getId();
            success = update(esId,indexTemplate);
        }else{
            success = deleteAndCreate(totalHit,indexTemplate);
        }
        if (success){
            log.debug("Index success with book "+isbn);
        }
    }

    private void removeIndex(BookIndexMessage message){
        String isbn = message.getIsbn();
        DeleteByQueryRequestBuilder  builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(BookIndexKey.ISBN,isbn))
                .source(INDEX_NAME);
        log.debug("Delete by query for book:"+builder);
        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        log.debug("Delete total:"+deleted);

        if (deleted <= 0){
            this.remove(isbn,message.getRetry()+1);
        }
    }

    @Override
    public void index(String isbn) {
        this.index(isbn,0);
    }

    private void index(String isbn,int retry){
        if (retry > BookIndexMessage.MAX_RETRY){
            log.error("Retry index times over 3 for book:"+isbn+" Please check it!");
            return;
        }
        BookIndexMessage message = new BookIndexMessage(isbn,BookIndexMessage.INDEX,retry);
        try {
            this.kafkaTemplate.send(INDEX_TOPIC,objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Json encode error for :" + message);
        }
    }

    private boolean create(BookIndexTemplate indexTemplate){
        if (!updateSuggest(indexTemplate)){
            return false;
        }
        try {
            IndexResponse response = this.esClient.prepareIndex(INDEX_NAME,INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON).get();
            log.debug("Create index with book:"+indexTemplate.getIsbn());
            if (response.status() == RestStatus.CREATED){
                return true;
            }else{
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index book"+indexTemplate.getIsbn(),e.getMessage());
            return false;
        }
    }

    private boolean update(String esId,BookIndexTemplate indexTemplate){
        if (!updateSuggest(indexTemplate)){
            return false;
        }
        try {
            UpdateResponse response = this.esClient.prepareUpdate(INDEX_NAME,INDEX_TYPE,esId)
                    .setDoc(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON).get();
            log.debug("Update index with book:"+indexTemplate.getIsbn());
            if (response.status() == RestStatus.OK){
                return true;
            }else{
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index book"+indexTemplate.getIsbn(),e.getMessage());
            return false;
        }
    }

    private boolean deleteAndCreate(long totalHit,BookIndexTemplate indexTemplate){
        DeleteByQueryRequestBuilder  builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(BookIndexKey.ISBN,indexTemplate.getIsbn()))
                .source(INDEX_NAME);
        log.debug("Delete by query for book:"+builder);
        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted != totalHit){
            log.warn("Need delete {},but {} was deleted!",totalHit,deleted);
            return false;
        }else{
            return create(indexTemplate);
        }
    }


    @Override
    public void remove(String isbn) {
        this.remove(isbn,0);
    }

    @Override
    public ResultVO query(String msg,int start,int size) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.filter(
//                QueryBuilders.termQuery(BookIndexKey.BOOKNAME,msg)
//        );
//        boolQuery.filter(
//                QueryBuilders.termQuery(BookIndexKey.AUTHOR,msg)
//        );
        //提高书名搜索权重
//        boolQuery.must(
//            QueryBuilders.matchQuery(
//                BookIndexKey.BOOKNAME,msg).boost(2.0f));
        boolQuery.must(
                QueryBuilders.multiMatchQuery(msg,
                        BookIndexKey.BOOKNAME,BookIndexKey.AUTHOR,BookIndexKey.TAG,BookIndexKey.LABLE
                ));
        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery)
                .addSort(
                        BookIndexKey.NUM, SortOrder.DESC
                ).setFrom(start)
                .setSize(size).setFetchSource(BookIndexKey.ISBN,null);
        log.debug(requestBuilder.toString());
        List<String> isbns = new ArrayList<>();
        SearchResponse response = requestBuilder.get();
        if (response.status() != RestStatus.OK){
            log.warn("Search status is no ok for " + requestBuilder);
            return ResultVOUtil.success();
        }
        for (SearchHit hit : response.getHits()){
            isbns.add((String)hit.getSourceAsMap().get(BookIndexKey.ISBN));
        }
        List<BookMessage> resultList = new ArrayList<>();
        for (String isbn:isbns){
            resultList.add(repository.findById(isbn).get());
        }
        List<SearchVO> searchVOList = BookMessage2SearchVOConverter.convert(resultList);
        return ResultVOUtil.success(searchVOList);
    }

    @Override
    public ResultVO suggest(String prefix) {
        CompletionSuggestionBuilder suggestion = SuggestBuilders.
                completionSuggestion("suggests").prefix(prefix).size(5);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("autocomplete",suggestion);
        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .suggest(suggestBuilder);
        log.debug(requestBuilder.toString());
        SearchResponse response = requestBuilder.get();
        Suggest suggest = response.getSuggest();
        Suggest.Suggestion result = suggest.getSuggestion("autocomplete");
        int maxSuggest = 0;
        Set<String> suggestSet = new HashSet<>();

        for (Object term:result.getEntries()){
            if (term instanceof CompletionSuggestion.Entry){
                CompletionSuggestion.Entry item = (CompletionSuggestion.Entry)term;
                if (item.getOptions().isEmpty()){
                    continue;
                }
                for (CompletionSuggestion.Entry.Option option:item.getOptions()){
                    String tip = option.getText().string();
                    if (suggestSet.contains(tip)){
                        continue;
                    }
                    suggestSet.add(tip);
                    maxSuggest++;
                }
            }
            if (maxSuggest > 5){
                break;
            }
        }
        List<String> suggests = Lists.newArrayList(suggestSet.toArray(new String[]{}));
        return ResultVOUtil.success(suggests);
    }

    private boolean updateSuggest(BookIndexTemplate indexTemplate){
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(
                this.esClient, AnalyzeAction.INSTANCE,INDEX_NAME,indexTemplate.getAuthor(),
                indexTemplate.getBookName());
        requestBuilder.setAnalyzer("ik_max_word");
        AnalyzeResponse response = requestBuilder.get();
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
        if (tokens == null){
            log.warn("Can not analyze token for book:"+indexTemplate.getIsbn());
            return false;
        }

        List<BookSuggest> suggests = new ArrayList<>();
        for (AnalyzeResponse.AnalyzeToken token : tokens){
            // 排除数字类型 && 小于2个字符的分词
            if ("<NUM>".equals(token.getType()) || token.getTerm().length() < 2){
                continue;
            }
            BookSuggest suggest = new BookSuggest();
            suggest.setInput(token.getTerm());
            suggests.add(suggest);
        }
        //定制化自动补全-作者
        BookSuggest suggest = new BookSuggest();
        suggest.setInput(indexTemplate.getAuthor());
        suggests.add(suggest);
        //定制化自动补全-书名
        BookSuggest suggest2 = new BookSuggest();
        suggest2.setInput(indexTemplate.getBookName());
        suggests.add(suggest2);
        indexTemplate.setSuggests(suggests);
        return true;
    }

    private void remove(String isbn,int retry){
        if (retry > BookIndexMessage.MAX_RETRY){
            log.error("Retry remove times over 3 for book:"+isbn+" Please check it!");
            return;
        }
        BookIndexMessage message = new BookIndexMessage(isbn,BookIndexMessage.REMOVE,retry);
        try {
            this.kafkaTemplate.send(INDEX_TOPIC,objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Json encode error for :" + message);
        }
    }

}
