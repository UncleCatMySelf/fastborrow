package com.mobook.fastborrow.dataobject;

import com.mobook.fastborrow.enums.AdverStatusEnum;
import com.mobook.fastborrow.utils.EnumUtil;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 广告类
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:37 2018\6\15 0015
 */
@Entity
@Data
@DynamicUpdate
public class Advertising implements Serializable {

    private static final long serialVersionUID = 6522873745970229948L;

    /**广告id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adverId;

    /**广告名称*/
    private String adverName;

    /**介绍描述*/
    private String adverInfo;

    /**图片链接*/
    private String image;

    /**显示位置*/
    private Integer adverAddress;

    /**显示状态*/
    private Integer adverStatus = AdverStatusEnum.UP.getCode();

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    public AdverStatusEnum getAdverStatusEnum(){
        return EnumUtil.getByCode(adverStatus, AdverStatusEnum.class);
    }

}
