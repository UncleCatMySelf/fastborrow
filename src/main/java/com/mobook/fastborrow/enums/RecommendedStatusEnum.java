package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:13 2018\7\11 0011
 */
@Getter
public enum RecommendedStatusEnum implements CodeEnum {

    UP(0 , "上线"),
    DOWN(1, "下线"),
    ;
    private Integer code;

    private String message;

    RecommendedStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
