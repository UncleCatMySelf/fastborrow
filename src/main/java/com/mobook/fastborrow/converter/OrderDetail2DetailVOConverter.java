package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.OrderDetail;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.vo.DeliveryVO;
import com.mobook.fastborrow.vo.DetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:56 2018\8\9 0009
 */
public class OrderDetail2DetailVOConverter {

    public static DetailVO convert(OrderDetail orderDetail){
        DetailVO deliveryVO = new DetailVO();
        BeanUtils.copyProperties(orderDetail,deliveryVO);
        return deliveryVO;
    }

    public static List<DetailVO> convert(List<OrderDetail> orderDetailList){
        return orderDetailList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<DetailVO> convert(Page<OrderDetail> orderDetailPage){
        return orderDetailPage.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
