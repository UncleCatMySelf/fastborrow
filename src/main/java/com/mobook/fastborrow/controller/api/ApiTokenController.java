package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:32 2018\7\6 0006
 */
@RestController
@RequestMapping("/api/v1/token")
public class ApiTokenController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get_token",method = RequestMethod.POST)
    public ResultVO getToken(String code){
        if (StringUtils.isEmpty(code)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        return userService.getToken(code);
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ResultVO verify(String token){
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        return userService.verifyToken(token);
    }

    @RequestMapping(value = "/save_user_info",method = RequestMethod.POST)
    public ResultVO saveUserInfo(String userid,String avatarUrl,String city,String country,Integer gender,String nickName,String province){
        if (userid.isEmpty()){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        User user = userService.findOne(Integer.parseInt(userid));
        User item = new User(nickName,null,avatarUrl,city,country,gender,province,0,new BigDecimal(0.00),new BigDecimal(0.00),null,null,null);
        item.setUserId(user.getUserId());
        item.setOpenId(user.getOpenId());
        item.setCollectionNum(user.getCollectionNum());
        item.setLibraryNum(user.getLibraryNum());
        userService.save(item);
        return ResultVOUtil.success();
    }

}
