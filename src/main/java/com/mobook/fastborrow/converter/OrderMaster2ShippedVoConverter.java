package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.vo.DeliveryVO;
import com.mobook.fastborrow.vo.ShippedVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:24 2018\8\9 0009
 */
public class OrderMaster2ShippedVoConverter {

    public static ShippedVO convert(OrderMaster orderMaster){
        ShippedVO shippedVO = new ShippedVO();
        BeanUtils.copyProperties(orderMaster,shippedVO);
        return shippedVO;
    }

    public static List<ShippedVO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<ShippedVO> convert(Page<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
