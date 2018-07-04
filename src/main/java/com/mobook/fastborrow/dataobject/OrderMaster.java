package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:41 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class OrderMaster implements Serializable {
    private static final long serialVersionUID = 3112469349073347561L;

    /**订单id生成*/
    @Id
    private String orderId;

    /**快递单号*/
    private String expressNum;

    /**买家名称*/
    private String buyerName;

    /**买家电话*/
    private String buyerPhone;

    /**买家地址*/
    private String buyerAddress;

    /**买家Openid*/
    private String buyerOpenid;

    /**订单押金*/
    private BigDecimal orderDeposit;

    /**实际付款*/
    private BigDecimal orderPayment;

    /**快递费用*/
    private BigDecimal orderExpress;

    /**订单金额*/
    private BigDecimal orderAmount;

    /**子订单数量*/
    private Integer orderNum;

    /**续借期限*/
    private Integer orderTime;

    /**订单状态*/
    private Integer orderStatus;

    /**支付状态*/
    private Integer payStatus;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
