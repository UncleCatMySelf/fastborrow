package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.BookWhere;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:37 2018\6\30 0030
 */
public interface BookWhereService {

    BookWhere save(BookWhere bookWhere);

    List<BookWhere> findAll();

    BookWhere findOne(Integer id);

    BookWhere findByWhereTag(String whereTag);

    BookWhere findByWhereName(String whereName);

}
