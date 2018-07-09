package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.converter.HotKey2HotKeyVOConverter;
import com.mobook.fastborrow.dataobject.HotKey;
import com.mobook.fastborrow.service.HotKeyService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.HotKeyVo;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:31 2018\7\9 0009
 */
@RestController
@RequestMapping("/api/v1/hotkey")
@Slf4j
public class ApiHotKeyController {

    @Autowired
    private HotKeyService hotKeyService;

    @GetMapping("/list")
    public ResultVO<List<HotKeyVo>> list(){
        List<HotKey> hotKeyList = hotKeyService.findAll();
        List<HotKeyVo> hotKeyVoList = HotKey2HotKeyVOConverter.convert(hotKeyList);
        return ResultVOUtil.success(hotKeyVoList);
    }
}
