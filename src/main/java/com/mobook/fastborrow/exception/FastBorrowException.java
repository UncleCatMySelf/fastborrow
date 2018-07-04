package com.mobook.fastborrow.exception;

import com.mobook.fastborrow.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:12 2018\6\13 0013
 */
@Getter
public class FastBorrowException extends RuntimeException {

    private Integer code;

    public FastBorrowException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public FastBorrowException(Integer code, String message){
        super(message);
        this.code = code;
    }

}
