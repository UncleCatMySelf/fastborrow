package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:47 2018\8\9 0009
 */
@Data
public class DeliveryVO {

    /**订单id生成*/
    private String orderId;

    /**快递单号*/
    private String expressNum;

    /**买家电话*/
    private String buyerPhone;

    /**实际付款*/
    private BigDecimal orderPayment;

    /**子订单数量*/
    private Integer orderNum;

    /**续借期限*/
    private Integer orderTime;

    /**备注*/
    private String note;

    /**下单时间*/
    private Date createTime;

    /**最新位置*/
    private String position;
}
