package com.mobook.fastborrow.form;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:14 2018\6\30 0030
 */
@Data
public class BookMessageForm {

    private String mobookId;

    /**书名*/
    @NotEmpty(message = "书名不能为空")
    private String bookName;

    /**ISBN*/
    @NotEmpty(message = "ISBN不能为空")
    private String isbn;

    /**价格*/
    @Digits(integer = 3,fraction = 2)
    private BigDecimal price;

    /**位置*/
    private Integer whereTag;

    /**作者*/
    @NotEmpty(message = "作者不能为空")
    private String author;

    /**作者简介*/
    @NotEmpty(message = "作者简介不能为空")
    private String authorInfo;

    /**出版社*/
    @NotEmpty(message = "出版社不能为空")
    private String press;

    /**出版时间*/
    @NotEmpty(message = "出版时间不能为空")
    private String pressTime;

    /**特色简介*/
    @NotEmpty(message = "特色简介不能为空")
    private String info;

    /**主要内容*/
    @NotEmpty(message = "主要内容不能为空")
    private String summary;

    /**目录*/
    @NotEmpty(message = "目录不能为空")
    private String catalog;

    /**封面*/
    @NotEmpty(message = "封面不能为空")
    private String images;

    /**标签*/
    @NotEmpty(message = "标签不能为空")
    private String lable;

    @NotEmpty(message = "长图简介不能为空")
    private String imageContext;

    /**分类数值*/
    private Integer tagNum;
}
