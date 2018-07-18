package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:00 2018\6\27 0027
 */
public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    Collection findByUserIdAndIsbnAndColStatus(@Param("userId") Integer userId,@Param("isbn") String isbn,@Param("colStatus") Integer colStatus);

}
