package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:32 2018\7\4 0004
 */
@Data
public class WinningVO {

    @JsonProperty("id")
    private String isbn;

    @JsonProperty("name")
    private String bookName;

    private Double score;

    private BigDecimal price;

    private String info;

    @JsonProperty("url")
    private String images;

}
