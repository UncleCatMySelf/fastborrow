package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:06 2018\7\4 0004
 */
@Data
public class AdvertisingVO {

    @JsonProperty("name")
    private String adverName;

    @JsonProperty("url")
    private String image;

}
