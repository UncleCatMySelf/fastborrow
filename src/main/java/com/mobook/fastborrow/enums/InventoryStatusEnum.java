package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:09 2018\7\17 0017
 */
@Getter
public enum InventoryStatusEnum implements CodeEnum {
    IN(1,"在库"),
    NO(2,"外借"),
    ;
    private Integer code;

    private String message;

    InventoryStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
