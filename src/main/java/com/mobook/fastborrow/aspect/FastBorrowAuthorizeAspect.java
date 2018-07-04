package com.mobook.fastborrow.aspect;

import com.mobook.fastborrow.constant.CookieConstant;
import com.mobook.fastborrow.constant.LogConstant;
import com.mobook.fastborrow.constant.LogMsgConstant;
import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.exception.FastBorrowAuthorizeException;
import com.mobook.fastborrow.utils.CookieUtil;
import com.mobook.fastborrow.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:44 2018\6\13 0013
 */
@Aspect
@Component
@Slf4j
public class FastBorrowAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.mobook.fastborrow.controller.Fb*.*(..))" +
            "&& !execution(public * com.mobook.fastborrow.controller.FbBackUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn(LogUtil.formatLogMessage(LogConstant.ASPECT_LOGIN_VERIFY, LogMsgConstant.ASPECT_COOKIE_NO));
            throw new FastBorrowAuthorizeException();
        }
        //去Redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn(LogUtil.formatLogMessage(LogConstant.ASPECT_LOGIN_VERIFY, LogMsgConstant.ASPECT_REDIS_NO));
            throw new FastBorrowAuthorizeException();
        }
    }

}
