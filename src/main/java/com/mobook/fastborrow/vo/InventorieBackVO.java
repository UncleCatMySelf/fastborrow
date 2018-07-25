package com.mobook.fastborrow.vo;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:40 2018\7\25 0025
 */
@Data
public class InventorieBackVO {

    private String bookName;

    private String isbn;

    private Integer numIn;

    private Integer numOut;

    private Integer sum;

}
