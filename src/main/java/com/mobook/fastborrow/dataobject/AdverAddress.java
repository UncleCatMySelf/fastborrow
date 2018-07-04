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
 * 广告显示位置
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:43 2018\6\15 0015
 */
@Entity
@Data
@DynamicUpdate
public class AdverAddress implements Serializable {

    private static final long serialVersionUID = 824669707564340130L;
    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**名称*/
    private String advAdName;

    /**数字*/
    private Integer advAdNum;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
