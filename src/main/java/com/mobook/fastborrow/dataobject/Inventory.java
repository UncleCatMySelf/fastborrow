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
 * 库存表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:25 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Inventory implements Serializable {
    private static final long serialVersionUID = 5075070433054641745L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mobookId;

    private String isbn;

    private String whereTag;

    /**状态*/
    private Integer status;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
