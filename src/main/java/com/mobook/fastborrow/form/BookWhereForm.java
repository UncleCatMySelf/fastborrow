package com.mobook.fastborrow.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:53 2018\6\30 0030
 */
@Data
public class BookWhereForm {

    private Integer id;

    @NotEmpty(message = "位置名称不能为空")
    private String whereName;

    @NotEmpty(message = "标签不能为空")
    private String whereTag;
}
