package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.NewBookVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:12 2018\7\4 0004
 */
public class BookMessage2NewBookVOConverter {

    public static NewBookVO convert(BookMessage bookMessage){
        NewBookVO newBookVO = new NewBookVO();
        BeanUtils.copyProperties(bookMessage,newBookVO);
        return newBookVO;
    }

    public static List<NewBookVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }

}
