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
 * 分类表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:48 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Tag implements Serializable {
    private static final long serialVersionUID = -6784979755983658547L;

    /**id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    /**分类名*/
    private String tagName;

    /**分类值*/
    private Integer tagNum;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
