package com.mobook.fastborrow.constant;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:38 2018\7\6 0006
 */
public interface WXLogMsgConstant {

    Integer WX_PARAM_CODE = 400;

    Integer WX_ERROR_OPENID_CODE = 401;

    Integer WX_ERRORCODE_CODE = 402;

    Integer WX_REPEAT_ADD_CODE = 600;

    Integer WX_SUCCESS = 777;

    Integer WX_DEPOSIT_CODE = 888;

    Integer WX_PDOO = 889;

    String WX_PARAM = "参数错误，请重新传递";

    String WX_ERROR_OPENID = "获取session_key及openID时异常，微信内部错误";

    String WX_ERRORCODE = "请求失败返回ERRCODE";

    String WX_REPEAT_ADD = "重复添加";

    String WX_DEPOSIT = "请充值押金";

    String WX_PDOO_MSG = "禁止重复下单";
}
