package com.mobook.fastborrow.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:17 2018\7\9 0009
 */
@Data
public class HotKeyForm {

    private Integer id;

    @NotEmpty(message = "关键字不能为空")
    private String hotkeyName;

    private Integer hotkeyType;

}
