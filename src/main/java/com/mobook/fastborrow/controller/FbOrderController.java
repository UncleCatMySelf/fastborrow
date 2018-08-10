package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.converter.OrderDetail2DetailVOConverter;
import com.mobook.fastborrow.converter.OrderMaster2DeliveryVOConverter;
import com.mobook.fastborrow.converter.OrderMaster2ShippedVoConverter;
import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.dataobject.OrderDetail;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.enums.InventoryStatusEnum;
import com.mobook.fastborrow.enums.OrderStatusEnum;
import com.mobook.fastborrow.service.BookWhereService;
import com.mobook.fastborrow.service.InventoryService;
import com.mobook.fastborrow.service.OrderDetailService;
import com.mobook.fastborrow.service.OrderMasterService;
import com.mobook.fastborrow.utils.MAVUtils;
import com.mobook.fastborrow.vo.DeliveryVO;
import com.mobook.fastborrow.vo.DetailVO;
import com.mobook.fastborrow.vo.ShippedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:35 2018\8\9 0009
 */
@Controller
@RequestMapping("/admin/order")
public class FbOrderController {

    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BookWhereService bookWhereService;

    @GetMapping("/Not_shipped")
    public ModelAndView shipped(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "size",defaultValue = "10") Integer size,
                                Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<OrderMaster> orderMasterPage = orderMasterService.findByOrderStatus(OrderStatusEnum.NEW.getCode(),request);
        List<ShippedVO> shippedVOList = OrderMaster2ShippedVoConverter.convert(orderMasterPage);
        map.put("orderMasterPage",orderMasterPage);
        map.put("shippedVOList",shippedVOList);
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView(MAVUriConstant.ORDER_SHIPPED,map);
    }

    @GetMapping("/delivery")
    public ModelAndView ToDelivery(@RequestParam(value = "orderId") String orderId,
                                   Map<String, Object> map){
        OrderMaster orderMaster = orderMasterService.getOne(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);
        List<DetailVO> detailVOList = OrderDetail2DetailVOConverter.convert(orderDetailList);
        for (DetailVO item : detailVOList){
            Inventory inventory = inventoryService.findByMobookId(item.getMobookId());
            item.setWhereTag(inventory.getWhereTag());
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        map.put("orderMaster",orderMaster);
        map.put("detailVOList",detailVOList);
        map.put("bookWhereList",bookWhereList);
        return new ModelAndView(MAVUriConstant.ORDER_INDEX,map);
    }

    /**
     * 待收货
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/Pending_delivery")
    public ModelAndView delivery(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<OrderMaster> orderMasterPage = orderMasterService.findByOrderStatus(OrderStatusEnum.PENDDELIVERY.getCode(),request);
        List<DeliveryVO> deliveryVOList = OrderMaster2DeliveryVOConverter.convert(orderMasterPage);
        map.put("orderMasterPage",orderMasterPage);
        map.put("deliveryVOList",deliveryVOList);
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView(MAVUriConstant.ORDER_DELIVERY,map);
    }

    /**
     * 派单
     * @param expressNum
     * @param orderId
     * @return
     */
    @PostMapping("/send")
    public ModelAndView send(@RequestParam("expressNum") String expressNum,
                             @RequestParam("orderId") String orderId){
        List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);
        for (OrderDetail item : orderDetailList){
            Inventory inventory = inventoryService.findByMobookId(item.getMobookId());
            inventory.setStatus(InventoryStatusEnum.NO.getCode());
            inventoryService.save(inventory);
        }
        OrderMaster orderMaster = orderMasterService.getOne(orderId);
        orderMaster.setExpressNum(expressNum);
        orderMaster.setOrderStatus(OrderStatusEnum.PENDDELIVERY.getCode());
        orderMasterService.save(orderMaster);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.ORDER_DELIVERY);
    }

}
