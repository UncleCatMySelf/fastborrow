package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.enums.AdverStatusEnum;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.repository.AdvertisingRepository;
import com.mobook.fastborrow.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:17 2018\6\16 0016
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private AdvertisingRepository repository;

    @Override
    public Advertising save(Advertising advertising) {
        return repository.save(advertising);
    }

    @Override
    public Page<Advertising> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Advertising findOne(Integer adverId) {
        return repository.findById(adverId).get();
    }

    @Override
    public List<Advertising> findByAdverAddressAndStatus(Integer adverAddress, Integer adverStatus) {
        return repository.findByAdverAddressAndAdverStatus(adverAddress, adverStatus);
    }

    @Override
    public Advertising onSale(Integer adverId) {
        Advertising advertising = repository.findById(adverId).get();
        if (advertising == null){
            throw new FastBorrowException(ResultEnum.AD_NOT_EXIT);
        }
        if (advertising.getAdverStatusEnum() == AdverStatusEnum.UP){
            throw new FastBorrowException(ResultEnum.AD_STATUS_ERROR);
        }
        //更新
        advertising.setAdverStatus(AdverStatusEnum.UP.getCode());
        return repository.save(advertising);
    }

    @Override
    public Advertising offSale(Integer adverId) {
        Advertising advertising = repository.findById(adverId).get();
        if (advertising == null){
            throw new FastBorrowException(ResultEnum.AD_NOT_EXIT);
        }
        if (advertising.getAdverStatusEnum() == AdverStatusEnum.DOWN){
            throw new FastBorrowException(ResultEnum.AD_STATUS_ERROR);
        }
        //更新
        advertising.setAdverStatus(AdverStatusEnum.DOWN.getCode());
        return repository.save(advertising);
    }
}
