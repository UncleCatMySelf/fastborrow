package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:38 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = -196430492753555363L;

    /**订单详情id*/
    @Id
    private String detailId;

    /**订单主id*/
    private String orderId;

    /**图书id*/
    private String mobookId;

    /**图书名称*/
    private String mobookName;

    /**图书价格*/
    private BigDecimal mobookPrice;

    /**图书图片*/
    private String mobookIcon;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
