package com.mobook.fastborrow.elastic.template;

import lombok.Data;

import java.util.List;

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

    //不在复制范围
    private String tag;

    private String lable;

    private Integer num;

    //type:completion
    private List<BookSuggest> suggests;

}
