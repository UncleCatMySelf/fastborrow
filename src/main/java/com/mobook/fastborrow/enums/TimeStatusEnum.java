package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:56 2018\8\8 0008
 */
@Getter
public enum TimeStatusEnum implements CodeEnum{
    THREE_MONTHS(1,"三个月"),
    HALF(2,"半年"),
    ONE_YEAR(3,"一年"),
    ;
    private Integer code;

    private String message;

    TimeStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
