package com.mobook.fastborrow.form;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:53 2018\6\30 0030
 */
@Data
public class TagForm {

    private Integer tagId;

    /**分类名*/
    private String tagName;

    /**分类值*/
    private Integer tagNum;

}
