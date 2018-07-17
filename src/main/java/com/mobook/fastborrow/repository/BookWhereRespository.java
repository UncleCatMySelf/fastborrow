package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.BookWhere;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:36 2018\6\30 0030
 */
public interface BookWhereRespository extends JpaRepository<BookWhere, Integer> {

    BookWhere findByWhereTag(String whereTag);

    BookWhere findByWhereName(String whereName);

}
