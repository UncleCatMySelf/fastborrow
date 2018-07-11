package com.mobook.fastborrow.dataobject;

import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.enums.RecommendedStatusEnum;
import com.mobook.fastborrow.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:24 2018\7\10 0010
 */
@Entity
@Data
@DynamicUpdate
public class Recommended implements Serializable {
    private static final long serialVersionUID = -892849022452714590L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String info;

    private String author;

    private String time;

    /**状态默认上线*/
    private Integer status = RecommendedStatusEnum.UP.getCode();

    /**样式类型*/
    private Integer css;

    private String images;

    private Date createTime;

    private Date updateTime;

    public RecommendedStatusEnum getRecStatusEnum(){
        return EnumUtil.getByCode(status, RecommendedStatusEnum.class);
    }

}
