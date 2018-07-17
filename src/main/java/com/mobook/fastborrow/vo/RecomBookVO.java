package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:26 2018\7\17 0017
 */
@Data
public class RecomBookVO {

    @JsonProperty("bookId")
    private String mobookId;

    private String bookName;

    @JsonProperty("bookAuthor")
    private String author;

    private String summary;

    @JsonProperty("url")
    private String images;

}
