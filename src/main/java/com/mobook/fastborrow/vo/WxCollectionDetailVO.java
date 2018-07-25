package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:32 2018\7\18 0018
 */
@Data
public class WxCollectionDetailVO {

    @JsonProperty("id")
    private String isbn;
    /**书名*/
    @JsonProperty("name")
    private String bookName;

    /**价格*/
    private BigDecimal price;

    private String author;

    private Integer num;

    @JsonProperty("url")
    private String images;

}
