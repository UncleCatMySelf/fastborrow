package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 积分表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:18 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Integrals implements Serializable {
    private static final long serialVersionUID = -6455132068780802921L;

    /**积分id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intId;

    /**用户id*/
    private Integer userId;

    /**用户名称*/
    private String userName;

    /**积分类型*/
    private Integer intLable;

    /**获取积分*/
    private Integer intScore;

    /**积分状态*/
    private Integer intStatus;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
