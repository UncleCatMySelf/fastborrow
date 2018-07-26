package com.mobook.fastborrow.elastic.template;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:59 2018\7\25 0025
 */
@Data
public class BookIndexMessage {

    public static final String INDEX = "index";
    public static final String REMOVE = "remove";

    public static final int MAX_RETRY = 3;

    private String isbn;
    private String operation;
    private int retry = 0;

    /**
     * 默认构造器 防止jackson序列化失败
     */
    public BookIndexMessage() {
    }

    public BookIndexMessage(String isbn, String operation, int retry) {
        this.isbn = isbn;
        this.operation = operation;
        this.retry = retry;
    }
}
