package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:09 2018\6\27 0027
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    OrderMaster findByBuyerOpenid (String buyerOpenid);

    List<OrderMaster> findByBuyerOpenidAndOrderStatus(String buyerOpenid,Integer orderStatus);


    Page<OrderMaster> findByOrderStatus(Integer orderStatus, Pageable pageable);

    OrderMaster findByBuyerOpenidAndOrderStatusIsNot(String buyerOpenid,Integer orderStatus);

    Page<OrderMaster> findByBuyerOpenidAndExpressNum(String buyerOpenid, String expressNum, Pageable pageable);

}
