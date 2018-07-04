package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.repository.BookWhereRespository;
import com.mobook.fastborrow.service.BookWhereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:38 2018\6\30 0030
 */
@Service
public class BookWhereServiceImpl implements BookWhereService {

    @Autowired
    private BookWhereRespository respository;

    @Override
    public BookWhere save(BookWhere bookWhere) {
        return respository.save(bookWhere);
    }

    @Override
    public List<BookWhere> findAll() {
        return respository.findAll();
    }

    @Override
    public BookWhere findOne(Integer id) {
        return respository.findById(id).get();
    }
}
