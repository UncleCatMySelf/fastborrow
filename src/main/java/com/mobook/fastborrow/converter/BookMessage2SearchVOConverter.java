package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.SearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:03 2018\7\20 0020
 */
public class BookMessage2SearchVOConverter {
    public static SearchVO convert(BookMessage bookMessage){
        SearchVO searchVO = new SearchVO();
        BeanUtils.copyProperties(bookMessage,searchVO);
        return searchVO;
    }

    public static List<SearchVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<SearchVO> convert(Page<BookMessage> bookMessagePage){
        return bookMessagePage.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
