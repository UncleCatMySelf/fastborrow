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
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:48 2018\7\9 0009
 */
@Data
@Entity
@DynamicUpdate
public class HotKey implements Serializable {

    private static final long serialVersionUID = 3374079196367784964L;

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**热门关键字名*/
    private String hotkeyName;

    /**热门关键字类型*/
    private Integer hotkeyType;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

}
