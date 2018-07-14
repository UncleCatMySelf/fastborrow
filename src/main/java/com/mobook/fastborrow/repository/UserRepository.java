package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:10 2018\6\27 0027
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByOpenId(String openId);

    Page<User> findByUserId(Integer userId, Pageable pageable);
}
