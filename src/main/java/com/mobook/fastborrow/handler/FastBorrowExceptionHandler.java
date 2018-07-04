package com.mobook.fastborrow.handler;

import com.mobook.fastborrow.exception.FastBorrowAuthorizeException;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:24 2018\6\13 0013
 */
@ControllerAdvice
public class FastBorrowExceptionHandler {

    /**
     * 拦截登录异常
     * @return
     */
    @ExceptionHandler(value = FastBorrowAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("/admin/login");
    }

    /**
     * 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = FastBorrowException.class)
    @ResponseBody
    public ResultVO handlerFastBorrowException(FastBorrowException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**
     * 未知系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handlerException(Exception e){
        return ResultVOUtil.error(666,e.getMessage());
    }

}
