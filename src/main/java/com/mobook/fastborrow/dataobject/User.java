package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:50 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class User implements Serializable {
    private static final long serialVersionUID = 3328877772032748907L;

    /**用户id生成*/
    @Id
    private Integer userId;

    /**用户名*/
    private String userName;

    /**微信openid*/
    private String openId;

    /**用户头像*/
    private String avatarUrl;

    /**城市*/
    private String city;

    /**国家*/
    private String country;

    /**性别*/
    private Integer gender;

    /**省份*/
    private String province;

    /**积分*/
    private Integer integral;

    /**用户余额*/
    private BigDecimal userBalance;

    /**用户押金*/
    private BigDecimal userDeposit;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    /**最近登录时间*/
    private Date lastLoginTime;

}
