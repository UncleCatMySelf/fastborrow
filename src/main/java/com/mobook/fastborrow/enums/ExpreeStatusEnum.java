package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:32 2018\8\10 0010
 */
@Getter
public enum  ExpreeStatusEnum implements CodeEnum  {
    TRANSIT(0,"在途"),
    RANGE_OF_PARTS(1,"揽件"),
    DIFFICULT(2,"疑难"),
    SIGN(3,"签收"),
    EXIT_SIGN(4,"退签"),
    SEND_PIECES(5,"派件"),
    RETURN(6,"退回"),
    ;

    private Integer code;

    private String message;

    ExpreeStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
