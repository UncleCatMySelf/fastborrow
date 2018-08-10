package com.mobook.fastborrow.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:28 2018\8\10 0010
 */
@Data
public class QueryDTO {

    private String num;

    private String state;

    private List<QueryExDTO> queryExDTOList;

}
