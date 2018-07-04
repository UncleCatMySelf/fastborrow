package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:52 2018\6\15 0015
 */
public interface AdvertisingRepository extends JpaRepository<Advertising, Integer> {
    // 根据位置与状态显示广告图片列表
    List<Advertising> findByAdverAddressAndAdverStatus(Integer adverAddress, Integer adverStatus);

}
