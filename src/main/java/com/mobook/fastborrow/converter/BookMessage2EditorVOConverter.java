package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.vo.EditorVO;
import com.mobook.fastborrow.vo.NewBookVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:54 2018\7\4 0004
 */
public class BookMessage2EditorVOConverter {

    public static EditorVO convert(BookMessage bookMessage){
        EditorVO editorVO = new EditorVO();
        BeanUtils.copyProperties(bookMessage,editorVO);
        return editorVO;
    }

    public static List<EditorVO> convert(List<BookMessage> bookMessageList){
        return bookMessageList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
