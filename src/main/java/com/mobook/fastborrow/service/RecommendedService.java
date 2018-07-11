package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Recommended;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:32 2018\7\10 0010
 */
public interface RecommendedService {

    Recommended findOne(Integer id);

    Page<Recommended> findAll(Pageable pageable);

    Recommended save(Recommended recommended);

    Page<Recommended> findAllByTitleIsLike(String title, Pageable pageable);

}
