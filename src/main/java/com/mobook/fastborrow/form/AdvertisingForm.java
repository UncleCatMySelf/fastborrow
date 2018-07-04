package com.mobook.fastborrow.form;

import lombok.Data;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:09 2018\6\16 0016
 */
@Data
public class AdvertisingForm {

    private Integer adverId;

    // 广告名称
    private String adverName;

    // 介绍描述
    private String adverInfo;

    // 图片链接
    private String image;

    // 显示位置
    private Integer adverAddress;

}
