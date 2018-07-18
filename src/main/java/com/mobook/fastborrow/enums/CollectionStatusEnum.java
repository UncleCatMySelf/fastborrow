package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 12:04 2018\7\18 0018
 */
@Getter
public enum CollectionStatusEnum implements CodeEnum {
    COLLECTION(1,"藏书"),
    LIBRARY(2,"借书"),
    DOWN(0,"下线"),
    ;
    private Integer code;

    private String message;

    CollectionStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
