package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.*;
import com.mobook.fastborrow.dataobject.BackUser;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.BackUserForm;
import com.mobook.fastborrow.service.BackUserService;
import com.mobook.fastborrow.utils.CookieUtil;
import com.mobook.fastborrow.utils.LogUtil;
import com.mobook.fastborrow.utils.MAVUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:14 2018\6\12 0012
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class FbBackUserController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BackUserService backUserService;

    @GetMapping("/toLogin")
    public ModelAndView toLogin(@Valid BackUserForm form, BindingResult bindingResult , HttpServletResponse response){
        BackUser backUser = new BackUser();
        try {
            if (bindingResult.hasErrors()){
                log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                        LogMsgConstant.USER_MSG_ERROR));
                return MAVUtils.setLoginResultMOV(MAVUriConstant.LOGIN,
                        LogMsgConstant.USER_MSG_ERROR,LogMsgConstant.USER_MSG_ERROR);
            }
            if (form.getBuName() != null){
                backUser = backUserService.findBackUserInfoByBuName(form.getBuName());
                if (backUser == null){
                    log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                            LogMsgConstant.USER_MSG_NAME_ERROR));
                    return MAVUtils.setLoginResultMOV(MAVUriConstant.LOGIN,
                            LogMsgConstant.USER_MSG_NAME_ERROR,null);
                }
            }
            if(!StringUtils.equals(form.getBuPassword(),backUser.getBuPassword())){
                log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                        LogMsgConstant.USER_MSG_PWD_ERROR));
                return MAVUtils.setLoginResultMOV(MAVUriConstant.LOGIN,
                        null,LogMsgConstant.USER_MSG_PWD_ERROR);
            }
        }catch (FastBorrowException e){
            log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                    e.getMessage()));
            return new ModelAndView(MAVUriConstant.LOGIN);
        }
        // 1、登录成功
        log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                LogMsgConstant.USER_MSG_LOGIN_SUCCESS));
        // 2、设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                LogMsgConstant.USER_MSG_TOKEN_REDIS));
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX ,token),backUser.getBuName(),expire, TimeUnit.SECONDS);
        // 3、设置token至cookie
        log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGIN,
                LogMsgConstant.USER_MSG_TOKEN_COOKIE));
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        return new ModelAndView(MAVUriConstant.INDEX);
    }

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView(MAVUriConstant.INDEX);
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView(MAVUriConstant.LOGIN);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        // 1、从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            // 2、清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            // 3、清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        log.info(LogUtil.formatLogMessage(LogConstant.BACKUSER_LOGOUT,
                LogMsgConstant.USER_MSG_LOGOUT_SUCCESS));
        return new ModelAndView(MAVUriConstant.LOGIN);
    }
}
