package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:36 2018\7\18 0018
 */
public interface CollectionService {

    Collection findByUserIdAndIsbn(Integer userId, String isbn, Integer colStatus);

    Collection save(Collection collection);

    Page<Collection> findAll(Pageable pageable);

    Integer countByUserIdAndColStatus(Integer userId, Integer colStatus);

    Page<Collection> findByUserIdAndColStatus(Integer userId, Integer colStatus, Pageable pageable);
}
