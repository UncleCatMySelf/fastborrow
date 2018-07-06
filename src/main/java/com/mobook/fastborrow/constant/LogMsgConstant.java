package com.mobook.fastborrow.constant;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:53 2018\6\13 0013
 */
public interface LogMsgConstant {
//后台登录
    String USER_MSG_ERROR = "信息错误";
    String USER_MSG_NAME_ERROR = "用户名错误";
    String USER_MSG_PWD_ERROR = "密码错误";
    String USER_MSG_LOGIN_SUCCESS = "登录成功";
    String USER_MSG_TOKEN_COOKIE = "设置token至cookie";
    String USER_MSG_TOKEN_REDIS = "设置token至redis";
//后台登出
    String USER_MSG_LOGOUT_SUCCESS = "登出成功";
//登录登出切面处理
    String ASPECT_COOKIE_NO = "Cookie中查不到token";
    String ASPECT_REDIS_NO = "Redis中查不到token";
//信息查询
    String TYPE_UESING = "本标签数值已引用";
}
