package com.mobook.fastborrow.elastic.template;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:26 2018\7\26 0026
 */
@Data
public class BookSuggest {

    private String input;
    private int weight = 10;//默认权重


}
