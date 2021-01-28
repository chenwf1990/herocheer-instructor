package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.vo.WechaLoginVo;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录api")
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/wecharLogin")
    @ApiOperation("微信登录")
    public ResponseResult wecharLogin(@RequestBody WechaLoginVo wechaLoginVo){
        loginService.wechatLogin(wechaLoginVo);
        return ResponseResult.ok();
    }

    @PostMapping("/loginTest")
    @ApiOperation("模拟测试登录")
    @AllowAnonymous
    public ResponseResult loginTest(@ApiParam("key值") @RequestParam String token,
                                    @ApiParam("用户id") @RequestParam(required = false) Long userId){
        if(StringUtils.isEmpty(token)){
            token = "chenweifeng";
        }
        loginService.loginTest(token,userId);
        return ResponseResult.ok().setMessage(token);
    }
}
