package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.BackUser;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:06 2018\6\12 0012
 */
public interface BackUserService {

    /**
     * 通过用户名查询后台人员信息
     * @param buName
     * @return
     */
    BackUser findBackUserInfoByBuName(String buName);

}
