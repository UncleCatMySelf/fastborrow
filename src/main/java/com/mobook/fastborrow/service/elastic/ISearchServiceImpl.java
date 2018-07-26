package com.mobook.fastborrow.service.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.elastic.template.BookIndexKey;
import com.mobook.fastborrow.elastic.template.BookIndexMessage;
import com.mobook.fastborrow.elastic.template.BookIndexTemplate;
import com.mobook.fastborrow.repository.BookMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics = INDEX_TOPIC)
    private void handleMessage(String content){
        System.err.println("-------执形handler");
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

        BookIndexTemplate indexTemplate = new BookIndexTemplate();
        BeanUtils.copyProperties(bookMessage,indexTemplate);

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
