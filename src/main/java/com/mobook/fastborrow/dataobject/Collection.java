package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 藏书表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:04 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Collection implements Serializable{
    private static final long serialVersionUID = -7349379168424393998L;

    /**id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer colId;

    /**用户id*/
    private Integer userId;

    /**isbn*/
    private String isbn;

    /**状态*/
    private Integer colStatus;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
