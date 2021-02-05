package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@Slf4j
@RequestMapping("/wechar")
@Api(tags = "微信信息")
public class WechatController {
    @Resource
    private WechatService wechatService;


    @GetMapping("/getWxInfo")
    @ApiOperation("获取微信信息")
    public ResponseResult<WxInfoVO> getWxInfo(@ApiParam("当前页面的地址") @RequestParam String pageUrl){
        WxInfoVO wxInfo = wechatService.getWxInfo(pageUrl);
        return ResponseResult.ok(wxInfo);
    }

    @PostMapping("ixmLoginUserIsExist")
    @ApiOperation(value = "判断i厦门微信登录用户是否存在")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信公众号code"),
            @ApiImplicitParam(name = "openid", value = "微信用户openid")
    })
    public ResponseResult<User> ixmLoginUserIsExist(HttpSession session, String code, String openid) {
        return ResponseResult.ok(wechatService.ixmUserIsLogin(session, code, openid));
    }

    @PostMapping("ixmLoginUrl")
    @AllowAnonymous
    @ApiOperation(value = "i厦门公众号登录页地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callBackUrl", value = "i厦门公众号登录页回调地址")
    })
    public ResponseResult<String> ixmLoginUrl(@NotBlank(message = "i厦门公众号登录页回调地址为空") String callBackUrl) {
        return ResponseResult.ok(wechatService.ixmLoginUrl(callBackUrl));
    }

    @PostMapping("ixmLogin")
    @ApiOperation(value = "i厦门公众号登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "微信用户openid"),
            @ApiImplicitParam(name = "token", value = "i厦门token")
    })
    public ResponseResult<User> ixmLogin(HttpServletRequest request, HttpSession session, @NotBlank(message = "微信用户openid不能为空") String openid,
                                               @NotBlank(message = "i厦门token不能为空") String token) {
        return ResponseResult.ok(wechatService.ixmLogin(request, session, openid, token));
    }
}
