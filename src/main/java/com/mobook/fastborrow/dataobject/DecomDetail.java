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
 * 推荐图书信息表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:15 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class DecomDetail implements Serializable {
    private static final long serialVersionUID = 2172188220553733817L;

    /**推荐表详情id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detId;

    /**推荐表id*/
    private Integer decId;

    /**图书名称*/
    private String detMobookName;

    /**图书简介*/
    private String detInfo;

    /**图书封面*/
    private String detImage;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
