package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:54 2018\7\25 0025
 */
@Getter
public enum  InventoryVOStatusEnum implements CodeEnum {
    Yes(1,"有货"),
    NO(2,"无货"),
    ;
    private Integer code;

    private String message;

    InventoryVOStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
