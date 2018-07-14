package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:43 2018\7\14 0014
 */
@Data
public class BookDetailVO {

    @JsonProperty("id")
    private String mobookId;

    @JsonProperty("name")
    private String bookName;

    private BigDecimal price;

    private String author;

    private String info;

    @JsonProperty("url")
    private String images;

    private Integer num;

    private String imageContext;

}
