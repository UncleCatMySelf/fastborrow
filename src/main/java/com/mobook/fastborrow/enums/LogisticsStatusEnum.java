package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:53 2018\7\19 0019
 */
@Getter
public enum LogisticsStatusEnum implements CodeEnum {
    UP(0 , "设为默认"),
    DOWN(1, "常态")
    ;

    private Integer code;

    private String message;

    LogisticsStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
