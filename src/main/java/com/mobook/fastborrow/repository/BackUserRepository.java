package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.BackUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:43 2018\6\12 0012
 */
public interface BackUserRepository extends JpaRepository<BackUser, Integer> {

    BackUser findByBuName(String buName);

}
