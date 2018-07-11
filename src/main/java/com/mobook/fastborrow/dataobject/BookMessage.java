package com.mobook.fastborrow.dataobject;

import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.print.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 图书信息表
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:53 2018\6\27 0027
 */
@Data
@Entity
@DynamicUpdate
public class BookMessage implements Serializable {

    private static final long serialVersionUID = -2014260536154596949L;

    /**墨书Id生成*/
    @Id
    private String mobookId;

    /**书名*/
    private String bookName;

    /**ISBN*/
    private String isbn;

    /**价格*/
    private BigDecimal price;

    /**位置*/
    private Integer whereTag;

    /**作者*/
    private String author;

    /**作者简介*/
    private String authorInfo;

    /**出版社*/
    private String press;

    /**出版时间*/
    private String pressTime;

    /**特色简介*/
    private String info;

    /**主要内容*/
    private String summary;

    /**目录*/
    private String catalog;

    /**封面*/
    private String images;

    /**标签*/
    private String lable;

    /**状态*/
    private Integer status = BookStatusEnum.UP.getCode();

    /**分类数值*/
    private Integer tagNum;

    /**评分*/
    private Double score = 10.00;

    /**借阅人数*/
    private Integer num = 0;

    /**推荐方案值*/
    private Integer recNum = 0;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    public BookStatusEnum getBookStatusEnum(){
        return EnumUtil.getByCode(status, BookStatusEnum.class);
    }
}
