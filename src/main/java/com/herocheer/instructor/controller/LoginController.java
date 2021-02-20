package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WechaLoginVo;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @AllowAnonymous
    public ResponseResult<UserInfoVo> wecharLogin(@RequestBody WechaLoginVo wechaLoginVo){
        UserInfoVo userVO = loginService.wechatLogin(wechaLoginVo);
        return ResponseResult.ok(userVO);
    }
}
