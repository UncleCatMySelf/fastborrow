package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:12 2018\6\16 0016
 */
@Getter
public enum AdverStatusEnum implements CodeEnum {
    UP(0 , "上线"),
    DOWN(1, "下线")
    ;

    private Integer code;

    private String message;

    AdverStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
