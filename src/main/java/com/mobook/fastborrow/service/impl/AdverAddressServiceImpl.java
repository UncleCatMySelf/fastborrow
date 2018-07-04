package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.AdverAddress;
import com.mobook.fastborrow.repository.AdverAddressRepository;
import com.mobook.fastborrow.service.AdverAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:59 2018\6\15 0015
 */
@Service
public class AdverAddressServiceImpl implements AdverAddressService {

    @Autowired
    private AdverAddressRepository repository;

    @Override
    public AdverAddress save(AdverAddress adverAddress) {
        return repository.save(adverAddress);
    }

    @Override
    public List<AdverAddress> findAll() {
        return repository.findAll();
    }

    @Override
    public AdverAddress findOne(Integer id) {
        return repository.findById(id).get();
    }
}
