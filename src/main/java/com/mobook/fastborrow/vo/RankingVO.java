package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:25 2018\7\17 0017
 */
@Data
public class RankingVO {

    private String mobookId;

    private String bookName;

    private BigDecimal price;

    private String author;

    private String info;

    private String images;

    private Double score;

    private Integer num;

    private Integer InventoryState;

}
