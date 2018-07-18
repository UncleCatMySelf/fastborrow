package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Collection;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:36 2018\7\18 0018
 */
public interface CollectionService {

    Collection findByUserIdAndIsbn(Integer userId, String isbn, Integer colStatus);

    Collection save(Collection collection);

}
