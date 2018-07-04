package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.BackUser;
import com.mobook.fastborrow.repository.BackUserRepository;
import com.mobook.fastborrow.service.BackUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:08 2018\6\12 0012
 */
@Service
@Slf4j
public class BackUserServiceImpl implements BackUserService {

    @Autowired
    private BackUserRepository repository;

    @Override
    public BackUser findBackUserInfoByBuName(String buName) {
        return repository.findByBuName(buName);
    }
}
