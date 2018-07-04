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
 * 推荐表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:11 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Decom implements Serializable {
    private static final long serialVersionUID = -3789459888192794701L;

    /**id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer decId;

    /**标题*/
    private String decTitle;

    /**简介*/
    private String decInfo;

    /**发布人*/
    private String decAuthor;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
