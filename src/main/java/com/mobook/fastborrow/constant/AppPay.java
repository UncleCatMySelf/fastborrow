package com.mobook.fastborrow.constant;

import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.enums.OrderStatusEnum;
import com.mobook.fastborrow.enums.PayStatusEnum;
import com.mobook.fastborrow.utils.KeyUtil;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:43 2018\7\28 0028
 */
public interface AppPay {

    Integer USER_DEPOSIT = 99;

    Integer DEPOSIT = 1;

    Integer ORDER = 2;

}
