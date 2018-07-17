package com.mobook.fastborrow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:25 2018\7\17 0017
 */
@Data
public class RecommendDetailVO {

    private Integer id;

    private String title;

    private String info;

    private String images;

    /**书籍*/
    @JsonProperty("list")
    private List<RecomBookVO> recomBookVOList;

}
