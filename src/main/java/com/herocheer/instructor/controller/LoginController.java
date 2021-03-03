package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/login")
@Api(tags = "测试类")
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/aes")
    @ApiOperation("AES加密/解密")
    @AllowAnonymous
    public ResponseResult add(@ApiParam("需要加密解密的文本") @RequestParam String text) {
        String aesStr;
        if(text.length() == 18){
            aesStr = AesUtil.encrypt(text);
        }else{
            aesStr = AesUtil.decrypt(text);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("text",text);
        map.put("aesStr",aesStr);
        return ResponseResult.ok(map);
    }
}
