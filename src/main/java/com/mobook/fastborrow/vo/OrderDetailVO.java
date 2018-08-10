package com.mobook.fastborrow.vo;

import com.mobook.fastborrow.dto.QueryExDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:09 2018\8\10 0010
 */
@Data
public class OrderDetailVO {

    private String status;

    private String lastTime;

    private QueryExDTO ExpressStr;

    private String logName;

    private String logPhone;

    private String logAddress;

    private String orderId;

    private List<OrderDetailBookVo> orderDetailBookVoList;

    private Integer time;

    private String invent;

    private String note;

    private Integer num;

    private BigDecimal amount;

    private BigDecimal sum;

    private String orderTime;

}
