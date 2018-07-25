package com.mobook.fastborrow.utils;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:22 2018\7\23 0023
 */
@Data
public class Demo {

    private Integer index;

    private String datas;

    public Demo(Integer index, String datas) {
        this.index = index;
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "index=" + index +
                ", datas='" + datas + '\'' +
                '}';
    }
}
