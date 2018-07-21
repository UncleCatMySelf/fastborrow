package com.mobook.fastborrow.vo;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:04 2018\7\21 0021
 */
@Data
public class RedisSearchVO {

    private Object key;

    private Double num;

    public RedisSearchVO() {

    }

    public RedisSearchVO(Object key, Double num) {
        this.key = key;
        this.num = num;
    }
}
