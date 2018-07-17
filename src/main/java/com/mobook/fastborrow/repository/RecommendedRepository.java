package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Recommended;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:29 2018\7\10 0010
 */
public interface RecommendedRepository extends JpaRepository<Recommended,Integer> {

    Page<Recommended> findAllByTitleIsLike(String title, Pageable pageable);

    Page<Recommended> findAllByStatus(Integer status, Pageable pageable);

}
