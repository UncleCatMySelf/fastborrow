package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.RecomBookVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:31 2018\7\17 0017
 */
public class BookMessage2RecomBookVOConverter {

    public static RecomBookVO convert(BookMessage bookMessage){
        RecomBookVO recomBookVO = new RecomBookVO();
        BeanUtils.copyProperties(bookMessage,recomBookVO);
        return recomBookVO;
    }

    public static List<RecomBookVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
