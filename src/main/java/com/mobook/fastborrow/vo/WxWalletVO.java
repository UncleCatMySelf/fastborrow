package com.mobook.fastborrow.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:40 2018\7\27 0027
 */
@Data
public class WxWalletVO {

    /**用户押金*/
    private BigDecimal userDeposit;

    /**积分*/
    private Integer integral;

}
