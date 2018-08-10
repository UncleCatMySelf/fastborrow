package com.mobook.fastborrow.dto;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:49 2018\8\9 0009
 */
@Data
public class Param {

    private String com;

    private String num;


    public Param() {
    }

    public Param(String com, String num) {
        this.com = com;
        this.num = num;
    }
}
