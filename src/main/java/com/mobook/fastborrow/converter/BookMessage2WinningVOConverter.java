package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.NewBookVO;
import com.mobook.fastborrow.vo.WinningVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:34 2018\7\4 0004
 */
public class BookMessage2WinningVOConverter {

    public static WinningVO convert(BookMessage bookMessage){
        WinningVO winningVO = new WinningVO();
        BeanUtils.copyProperties(bookMessage,winningVO);
        return winningVO;
    }

    public static List<WinningVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
