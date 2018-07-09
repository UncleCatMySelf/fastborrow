package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.HotKey;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:54 2018\7\9 0009
 */
public interface HotKeyService {

    HotKey findOne(Integer id);

    List<HotKey> findAll();

    HotKey save(HotKey hotKey);

}
