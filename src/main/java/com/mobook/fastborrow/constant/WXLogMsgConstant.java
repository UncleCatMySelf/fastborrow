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

    String WX_PARAM = "参数错误，请重新传递";

    String WX_ERROR_OPENID = "获取session_key及openID时异常，微信内部错误";

    String WX_ERRORCODE = "请求失败返回ERRCODE";
}
