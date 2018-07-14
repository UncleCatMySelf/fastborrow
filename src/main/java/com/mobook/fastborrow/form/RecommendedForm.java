package com.mobook.fastborrow.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:09 2018\7\11 0011
 */
@Data
public class RecommendedForm {

    private Integer id;

    @NotEmpty(message = "标题不能为空")
    private String title;

    @NotEmpty(message = "文案不能为空")
    private String info;

    @NotEmpty(message = "作者不能为空")
    private String author;

    @NotEmpty(message = "发行时间不能为空")
    private String time;

    private Integer cssstatus;

    @NotEmpty(message = "封面不能为空")
    private String images;


    private String[] bookMessIdList;

}
