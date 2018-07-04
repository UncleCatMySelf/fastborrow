package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:51 2018\7\4 0004
 */
@Data
public class EditorVO {

    @JsonProperty("id")
    private String mobookId;

    @JsonProperty("name")
    private String bookName;

    @JsonProperty("url")
    private String images;

}
