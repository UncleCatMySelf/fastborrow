package com.mobook.fastborrow.form;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:09 2018\7\11 0011
 */
@Data
public class RecommendedForm {

    private Integer id;

    private String title;

    private String info;

    private String author;

    private String time;

    private Integer cssstatus;

    private String images;

    private String[] bookMessIdList;

}
