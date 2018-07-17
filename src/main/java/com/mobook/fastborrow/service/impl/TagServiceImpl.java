package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Tag;
import com.mobook.fastborrow.repository.TagRepository;
import com.mobook.fastborrow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:44 2018\6\30 0030
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository repository;

    @Override
    public Tag save(Tag tag) {
        return repository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    public Tag findOne(Integer tagId) {
        return repository.findById(tagId).get();
    }

    @Override
    public Tag findByTagNum(Integer tagNum) {
        return repository.findByTagNum(tagNum);
    }

    @Override
    public Tag findByTagName(String tagName) {
        return repository.findByTagName(tagName);
    }
}
