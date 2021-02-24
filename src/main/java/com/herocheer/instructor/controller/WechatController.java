package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.SmsCodeUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Validated
public class WechatController extends BaseController {
    @Resource
    private WechatService wechatService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisClient redisClient;



    @GetMapping("/getWxInfo")
    @ApiOperation("获取微信信息")
    public ResponseResult<WxInfoVO> getWxInfo(@ApiParam("当前页面的地址") @RequestParam String pageUrl){
        WxInfoVO wxInfo = wechatService.getWxInfo(pageUrl);
        return ResponseResult.ok(wxInfo);
    }


    /**
     * ixm登录用户是存在的(@AllowAnonymous不能去掉)
     *
     * @param session 会话
     * @param code    代码
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("ixmLoginUserIsExist")
    @ApiOperation(value = "判断i厦门微信登录用户是否存在")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信公众号code"),
            @ApiImplicitParam(name = "openid", value = "微信用户openid")
    })
    public ResponseResult<User> ixmLoginUserIsExist(HttpSession session, @NotBlank(message = "微信公众号授权码不为空")  String code) {
        return ResponseResult.ok(wechatService.ixmUserIsLogin(session, code));
    }

    /**
     * ixm登录网址(@AllowAnonymous不能去掉)
     *
     * @param callBackUrl 回调url
     * @return {@link ResponseResult<String>}
     */
    @PostMapping("ixmLoginUrl")
    @AllowAnonymous
    @ApiOperation(value = "i厦门公众号登录页地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callBackUrl", value = "i厦门公众号登录页回调地址")
    })
    public ResponseResult<String> ixmLoginUrl(@NotBlank(message = "i厦门公众号登录页回调地址为空") String callBackUrl) {
        return ResponseResult.ok(wechatService.ixmLoginUrl(callBackUrl));
    }

    /**
     * ixm登录 (@AllowAnonymous不能去掉)
     *
     * @param request 请求
     * @param session 会话
     * @param openid  openid
     * @param token   令牌
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("ixmLogin")
    @ApiOperation(value = "i厦门公众号登录")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "微信用户openid"),
            @ApiImplicitParam(name = "token", value = "i厦门token")
    })
    public ResponseResult<User> ixmLogin(HttpServletRequest request, HttpSession session, @NotBlank(message = "微信用户openid不能为空") String openid,
                                               @NotBlank(message = "i厦门token不能为空") String token) {
        return ResponseResult.ok(wechatService.ixmLogin(request, session, openid, token));
    }

    /**
     * 获取短信验证码
     *
     * @param request 请求
     * @param phone   电话
     * @return {@link ResponseResult<User>}
     */
    @GetMapping("/sms/code")
    @ApiOperation(value = "短信验证码")
    @AllowAnonymous
    @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String",paramType = "query")
    public ResponseResult fetchSmsCode(@NotBlank(message = "手机号不能为空") String phone,HttpServletRequest request) {
        SmsCodeUtil.getSmsCode(phone);
        return ResponseResult.ok();
    }


    /**
     * 验证短信验证码
     *
     * @param phone   电话
     * @param code    验证码
     * @param request 请求
     * @return {@link ResponseResult<User>}
     */
    @GetMapping("/sms/binding")
    @ApiOperation(value = "手机绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String",paramType = "query")
    })
    public ResponseResult verifySmsCode(@NotBlank(message = "手机号不能为空") String phone,
                                         @NotBlank(message = "验证码不能为空") String code, HttpServletRequest request) {
        ResponseResult  result = SmsCodeUtil.verifySmsCode(phone, code);
        if("F".equals(result.getSuccess())){
            return ResponseResult.fail(result.getMessage());
        }

        // 验证成功后绑定手机和openid
        User user = userService.findUserByPhone(phone);
        if(ObjectUtils.isEmpty(user)){
            // 后台无记录，请前往社会指导员认证或联系管理员。
            throw new CommonException("绑定失败，查无此手机号用户");
        }

        // 获取当前用户的openid
        UserEntity correntUser = getUser(request);
        JSONObject JSONObj = JSONObject.parseObject(redisClient.get(correntUser.getToken()));
        user.setOpenid(JSONObj.getString("openid"));

        userService.update(user);
        return ResponseResult.ok();
    }

    /**
     * 通过openId获取微信用户
     *
     * @param openId 开放id
     * @return {@link ResponseResult<User>}
     */
    @GetMapping("/{openId:\\w+}")
    @ApiOperation("获取微信用户信息(openid)")
    public ResponseResult<User> fetchUserByOpenId(@ApiParam("用户ID") @PathVariable String openId,HttpServletRequest request){
        return ResponseResult.ok(userService.findUserByOpenId(openId));
    }

    /**
     * 通过电话获取用户
     *
     * @param phone 电话
     * @return {@link ResponseResult<User>}
     */
    @GetMapping("/phone/{phone:\\w+}")
    @ApiOperation("获取微信用户信息(phone)")
    public ResponseResult<User> fetchUserByPhone(@ApiParam("电话号码") @PathVariable String phone,HttpServletRequest request){
        return ResponseResult.ok(userService.findUserByPhone(phone));
    }


    /**
     * 通过用户ID获取当前用户信息
     *
     * @return {@link ResponseResult<User>}
     */
    @GetMapping("/correctUser")
    @ApiOperation("我的资料")
    public ResponseResult<User> fetchUserById(HttpServletRequest request){
        return ResponseResult.ok(userService.get(getCurUserId(request)));
    }

    /**
     * 编辑微信用户
     *
     * @param weChatUserVO VO
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("/weChatUser")
    @ApiOperation("编辑信息")
    public ResponseResult<User> editWeChatUser(@ApiParam("微信用户") @RequestBody WeChatUserVO weChatUserVO){
        return ResponseResult.ok(userService.modifyWeChatUser(weChatUserVO));
    }

}
