package com.mobook.fastborrow.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:17 2018\6\12 0012
 */
@Data
public class BackUserForm {

    @NotEmpty(message = "用户名不能为空")
    private String buName;      //用户名

    @NotEmpty(message = "用户密码不能为空")
    private String buPassword;  //用户密码

}
