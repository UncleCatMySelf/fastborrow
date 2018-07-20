package com.mobook.fastborrow.constant;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:18 2018\6\13 0013
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    String WX_TONEKN_PREFIX = "wx_token_%s";

    String WX_USER_SEARCH = "wx_user_%s";

    Integer EXPIRE = 7200;//2小时

    Integer WX_USER_EXPIRE = 86400;//24小时

}
