package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.BookDetailVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:53 2018\7\14 0014
 */
public class BookMessage2BookDetailVoConverter {

    public static BookDetailVO convert(BookMessage bookMessage){
        BookDetailVO bookDetailVO = new BookDetailVO();
        BeanUtils.copyProperties(bookMessage,bookDetailVO);
        return bookDetailVO;
    }

    public static List<BookDetailVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
