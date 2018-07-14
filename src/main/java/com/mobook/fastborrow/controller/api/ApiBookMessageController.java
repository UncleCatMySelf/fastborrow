package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.converter.*;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序图书业务
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 12:00 2018\7\4 0004
 */
@RestController
@RequestMapping("/api/v1/bookmessage")
@Slf4j
public class ApiBookMessageController {

    @Autowired
    private BookMessageService bookMessageService;

    @GetMapping("/nblist")
    public ResultVO<List<NewBookVO>> newBookList(){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.NEWBOOK.getCode());
        List<NewBookVO> newBookVOList = BookMessage2NewBookVOConverter.convert(bookMessageList);
        return ResultVOUtil.success(newBookVOList);
    }

    @GetMapping("/edlist")
    public ResultVO<List<EditorVO>> editorList(){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.EDITOR.getCode());
        List<EditorVO> editorVOList = BookMessage2EditorVOConverter.convert(bookMessageList);
        return ResultVOUtil.success(editorVOList);
    }

    @GetMapping("/wlist")
    public ResultVO<List<WinningVO>> winningList(){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.WINNING.getCode());
        List<WinningVO> winningVOList = BookMessage2WinningVOConverter.convert(bookMessageList);
        return ResultVOUtil.success(winningVOList);
    }

    @GetMapping("/hotSlist")
    public ResultVO<List<HotsSearchVO>> hotsSearchList(){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.HOTSEARCH.getCode());
        List<HotsSearchVO> hotsSearchVOList = BookMessage2HotsSearchVOConverter.convert(bookMessageList);
        return ResultVOUtil.success(hotsSearchVOList);
    }

    @GetMapping("/bookDetail")
    public ResultVO<BookDetailVO> getBookDetail(@RequestParam("mobookId") String mobookId){
        BookMessage bookMessage = bookMessageService.findOne(mobookId);
        BookDetailVO bookDetailVO = BookMessage2BookDetailVoConverter.convert(bookMessage);
        return ResultVOUtil.success(bookDetailVO);
    }

}
