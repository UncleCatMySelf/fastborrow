package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.DeliveryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:57 2018\8\9 0009
 */
public class OrderMaster2DeliveryVOConverter {

    public static DeliveryVO convert(OrderMaster orderMaster){
        DeliveryVO deliveryVO = new DeliveryVO();
        BeanUtils.copyProperties(orderMaster,deliveryVO);
        return deliveryVO;
    }

    public static List<DeliveryVO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<DeliveryVO> convert(Page<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
