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

    /**墨书id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bookName;

    private String isbn;

    /**总库存数*/
    private Integer num;

    /**现存数*/
    private Integer statusNum;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
