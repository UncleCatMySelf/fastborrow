package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:44 2018\7\27 0027
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {

    PENDPAY(1 , "待付款"),
    PENDDELIVERY(2, "待收货"),
    PENDRETURN(3,"待归还"),
    COMPLETED(4,"已完成"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
