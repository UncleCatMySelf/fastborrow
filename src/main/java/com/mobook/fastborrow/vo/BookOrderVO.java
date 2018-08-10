package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:24 2018\8\10 0010
 */
@Data
public class BookOrderVO {

    private String isbn;

    /**书名*/
    private String bookName;

    /**价格*/
    private BigDecimal price;

    /**星星*/
    private Double score = 10.00;

    /**借阅人数*/
    private Integer num = 0;

    /**封面*/
    private String images;

}
