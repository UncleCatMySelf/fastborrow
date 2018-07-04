package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Advertising;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:11 2018\6\16 0016
 */
public interface AdvertisingService {

    Advertising save(Advertising advertising);

    Page<Advertising> findAll(Pageable pageable);

    Advertising findOne(Integer adverId);

    List<Advertising> findByAdverAddressAndStatus(@Param("adverAddress") Integer adverAddress,
                                                  @Param("adverStatus") Integer adverStatus);

    Advertising onSale(Integer adverId);

    Advertising offSale(Integer adverId);
}
