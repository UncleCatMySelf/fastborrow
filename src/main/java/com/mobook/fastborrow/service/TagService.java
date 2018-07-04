package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Tag;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:42 2018\6\30 0030
 */
public interface TagService {

    Tag save(Tag tag);

    List<Tag> findAll();

    Tag findOne(Integer tagId);

}
