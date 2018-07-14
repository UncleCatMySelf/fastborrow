package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:34 2018\7\6 0006
 */
public interface UserService {

    ResultVO getToken(String code);

    User findOne(Integer userId);

    User save(User user);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> findByUserId(Integer userId, Pageable pageable);

    User findByOpenId(String openId);

    ResultVO verifyToken(String token);

}
