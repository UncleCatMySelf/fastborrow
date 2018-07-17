package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.NewBookVO;
import com.mobook.fastborrow.vo.RankingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:34 2018\7\17 0017
 */
public class BookMessage2RankingVOConverter {

    public static RankingVO convert(BookMessage bookMessage){
        RankingVO rankingVO = new RankingVO();
        BeanUtils.copyProperties(bookMessage,rankingVO);
        return rankingVO;
    }

    public static List<RankingVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<RankingVO> convert(Page<BookMessage> bookMessagePage,List<Integer> integerList){

        List<RankingVO> rankingVOList = bookMessagePage.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

        for (int i = 0 ; i < rankingVOList.size();i++){
            rankingVOList.get(i).setInventoryState(integerList.get(i));
        }
        return rankingVOList;
    }

}
