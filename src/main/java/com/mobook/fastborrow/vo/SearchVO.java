package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:31 2018\7\20 0020
 */
@Data
public class SearchVO {

    private String isbn;

    private String bookName;

    private BigDecimal price;

    private String author;

    private String info;

    private String images;

    private Double score;

    private Integer num;

}
