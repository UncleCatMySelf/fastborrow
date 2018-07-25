package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:45 2018\7\9 0009
 */
@Data
public class HotsSearchVO {

    @JsonProperty("id")
    private String isbn;

    @JsonProperty("name")
    private String bookName;

    private Integer num;

    @JsonProperty("url")
    private String images;

}
