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
 * 评论表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:09 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Comments implements Serializable {
    private static final long serialVersionUID = -1800279927691542604L;

    /**id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comId;

    /**用户id*/
    private Integer userId;

    /**图书id*/
    private String mobookId;

    /**内容*/
    private String comInfo;

    /**推荐分数*/
    private Integer comScore;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
