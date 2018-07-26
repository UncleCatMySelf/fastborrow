package com.mobook.fastborrow.elastic.template;

import lombok.Data;

/**
 * 索引结构模板
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:34 2018\7\25 0025
 */
@Data
public class BookIndexTemplate {

    private String isbn;

    private String bookName;

    private String author;

    private Integer num;

    private Integer tagNum;

    //标签
}
