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
 * 物流信息表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:35 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class Logistics implements Serializable {
    private static final long serialVersionUID = -8942415527515769956L;

    /**物流信息id自生*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    /**用户id*/
    private Integer userId;

    /**收件人*/
    private String logName;

    /**联系方式*/
    private String logPhone;

    /**邮编*/
    private String logZip;

    /**地址*/
    private String logAddress;

    /**区域*/
    private String logRegional;

    private Integer status;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
