package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.EditorVO;
import com.mobook.fastborrow.vo.HotsSearchVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:48 2018\7\9 0009
 */
public class BookMessage2HotsSearchVOConverter {

    public static HotsSearchVO convert(BookMessage bookMessage){
        HotsSearchVO hotsSearchVO = new HotsSearchVO();
        BeanUtils.copyProperties(bookMessage,hotsSearchVO);
        return hotsSearchVO;
    }

    public static List<HotsSearchVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
