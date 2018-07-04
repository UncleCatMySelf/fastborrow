package com.mobook.fastborrow.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:39 2018\6\12 0012
 */
@Entity
@Data
@DynamicUpdate
public class BackUser implements Serializable {

    private static final long serialVersionUID = -223964324351186190L;

    /**后台用户id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  buId;

    /**用户名*/
    private String buName;

    /**用户密码*/
    private String buPassword;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
