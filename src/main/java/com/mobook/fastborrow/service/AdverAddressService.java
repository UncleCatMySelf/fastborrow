package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.AdverAddress;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:55 2018\6\15 0015
 */
public interface AdverAddressService {

    // 添加一个类别
    AdverAddress save(AdverAddress adverAddress);

    // 返回所有数据
    List<AdverAddress> findAll();

    // 返回单个数据
    AdverAddress findOne(Integer id);

}
