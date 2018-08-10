package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:23 2018\8\10 0010
 */
@Data
public class OrderVO {

    //订单号
    private String orderId;

    //状态
    private String status;

    //时间
    private String time;

    //本数
    private Integer num;

    //金额
    private BigDecimal amount;

    //图书
    private List<BookOrderVO> bookOrderVOList;

}
