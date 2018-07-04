package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Comments;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:01 2018\6\27 0027
 */
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
