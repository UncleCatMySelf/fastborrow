package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.constant.*;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.exception.FastBorrowAuthorizeException;
import com.mobook.fastborrow.repository.UserRepository;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.HttpServiceUtils;
import com.mobook.fastborrow.utils.LogUtil;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.utils.WxUtils;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:34 2018\7\6 0006
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserRepository repository;

    @Override
    public ResultVO getToken(String code) {
        String[] results = new String[2];
        JSONArray wxResult = null;
        String openid = null;
        String session_key = null;
        String wxLoginUrl = AppConstant.WXURL+"appid="+AppConstant.APPID+"&secret="+AppConstant.APPSECRET+"&js_code="+code+"&grant_type="+AppConstant.GRANTTYPE;
        String result = HttpServiceUtils.sendGet(wxLoginUrl);
        if (StringUtils.isEmpty(result)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_ERROR_OPENID_CODE,WXLogMsgConstant.WX_ERROR_OPENID);
        }else{
            wxResult = JSONArray.fromObject("["+result+"]");
            if (wxResult == null){
                return ResultVOUtil.error(WXLogMsgConstant.WX_ERROR_OPENID_CODE,WXLogMsgConstant.WX_ERROR_OPENID);
            }else{
                if (wxResult.size() > 0){
                    for(int i = 0; i < wxResult.size(); i++){
                        JSONObject item = wxResult.getJSONObject(i);
                        try {
                            Object errCode = item.get("errcode");
                        }catch (Exception e){
                            log.error("【微信Token】:"+e.getMessage());
                            return ResultVOUtil.error(WXLogMsgConstant.WX_ERRORCODE_CODE,WXLogMsgConstant.WX_ERRORCODE);
                        }
                        session_key = (String)item.get("session_key");
                        log.info("【微信Token】session_key:"+session_key);
                        openid = (String)item.get("openid");
                        if (openid != null){
                            break;
                        }
                    }
                }
            }
        }
        results = granToken(openid);
        JSONObject object = new JSONObject();
        object.put("token",results[0]);
        object.put("userid",results[1]);
        JSONArray tokenJSON = new JSONArray();
        tokenJSON.add(object);
        return ResultVOUtil.success(tokenJSON);
    }

    @Override
    public User findOne(Integer userId) {
        return repository.findById(userId).get();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByOpenId(String openId) {
        return repository.findByOpenId(openId);
    }

    @Override
    public ResultVO verifyToken(String token) {
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        if (StringUtils.isEmpty(tokenValue)){
            return ResultVOUtil.success(false);
        }else{
            return ResultVOUtil.success(true);
        }
    }

    private String[] granToken(Object openid){
        String[] results = new String[2];
        //获取openid
        Integer userId = null;
        String wxOpenid = (String)openid;
        //数据库查看，是否存在openid，如果存在则不处理，如果不存在就新增一条用户数据
        User user = findByOpenId(wxOpenid);
        if (user != null){
            userId = user.getUserId();
        }else{
            User user1 = new User();
            user1.setOpenId(wxOpenid);
            userId = save(user1).getUserId();
        }
        String token  = saveToRedis(userId);
        //把令牌返回到客户端去 key:令牌、value：wxResult、uid、scope
        results[0] = token;
        results[1] = userId.toString();
        return results;
    }

    private String saveToRedis(Integer userId) {
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.WX_TONEKN_PREFIX ,token),String.valueOf(userId),expire, TimeUnit.SECONDS);
        return token;
    }
}
