package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.MsgCodeVO;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.AesUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CourseInfoService courseInfoService;

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
    public ResponseResult<UserInfoVo> ixmLoginUserIsExist(HttpSession session, String code, String openid) {
        return ResponseResult.ok(wechatService.ixmUserIsLogin(session, code,openid));
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
        // 获取当前用户信息
        String userInfo = redisClient.get(getCurTokenId(request));
        UserInfoVo infoVo = JSONObject.parseObject(userInfo,UserInfoVo.class);
        return ResponseResult.ok(wechatService.ixmLogin(request, session, openid, token,infoVo));
    }


    /**
     * ixmAPP应用登录网址
     *
     * @param redirectUri 重定向的uri
     * @return {@link ResponseResult<String>}
     */
    @PostMapping("ixmAppLoginUrl")
    @ApiOperation(value = "i厦门APP登录页回调地址")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "redirectUri", value = "i厦门APP登录页回调地址"),
    })
    public ResponseResult<String> ixmAppLoginUrl(@NotBlank(message = "i厦门APP登录页回调地址不能为空") String redirectUri) {
        return ResponseResult.ok(wechatService.ixmAppLoginUrl(redirectUri));
    }

    /**
     * ixmAPP应用登录
     *
     * @param request     请求
     * @param session     会话
     * @param code        代码
     * @param redirectUri 重定向的uri
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("ixmAppLogin")
    @ApiOperation(value = "i厦门APP登录")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信用户openid"),
            @ApiImplicitParam(name = "redirectUri", value = "i厦门APP登录页回调地址")
    })
    public ResponseResult<UserInfoVo> ixmAppLogin(HttpServletRequest request, HttpSession session,
                                                  @NotBlank(message = "i厦门APP code不能为空") String code,
                                                  @NotBlank(message = "i厦门APP登录页回调地址不能为空") String redirectUri) {
        return  ResponseResult.ok(wechatService.ixmAppLogin(request, session, code, redirectUri));
    }

    /**
     * 厦门市民卡APP登录
     *
     * @param session 会话
     * @param token   令牌
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("smkLogin")
    @ApiOperation(value = "厦门市民卡APP登录")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "市民卡token"),
    })
    public ResponseResult<UserInfoVo> smkLogin(HttpSession session, @NotBlank(message = "市民卡token不能为空") String token) {
        return ResponseResult.ok(wechatService.smkLogin(session, token));
    }

    /**
     * 获取短信验证码
     *
     * @param request    请求
     * @param msgCodeVO 用户实体
     * @return {@link ResponseResult}
     */
    @PostMapping("/sms/code")
    @ApiOperation(value = "短信验证码")
    public ResponseResult fetchSmsCode( @ApiParam("短信验证码VO") @RequestBody MsgCodeVO msgCodeVO, HttpServletRequest request) {
        // 限制短信验证码的使用（很贵）
        User user = userService.findUserByPhone(msgCodeVO.getPhone());
        if(ObjectUtils.isEmpty(user)){
            // 后台无记录，请前往社会指导员认证或联系管理员。
            throw new CommonException("您未注册指导员，请联系管理员");
        }

        if(StringUtils.hasText(user.getOpenid())){
            // 后台无记录，请前往社会指导员认证或联系管理员。
            throw new CommonException("您已绑定过了");
        }
        // 发送短信验证码
        SmsCodeUtil.getSmsCode(AesUtil.decrypt(msgCodeVO.getPhone()));
        return ResponseResult.ok();
    }


    /**
     * 验证短信验证码
     *
     * @param request 请求
     * @return {@link ResponseResult<User>}
     */
    @PostMapping("/sms/binding")
    @ApiOperation(value = "手机绑定")
    public ResponseResult verifySmsCode(@ApiParam("短信验证码VO")  @RequestBody MsgCodeVO msgCodeVO, HttpServletRequest request) {

        ResponseResult  result = SmsCodeUtil.verifySmsCode(AesUtil.decrypt(msgCodeVO.getPhone()), msgCodeVO.getCode());
        if("F".equals(result.getSuccess())){
            return ResponseResult.fail(result.getMessage());
        }

        // 绑定功能
        UserEntity correntUser = getUser(request);
        UserInfoVo UserInfo = wechatService.bindingWeChat(correntUser, msgCodeVO.getPhone());

        // 绑定成功之后要替换当前用户信息
        redisClient.set(correntUser.getToken(),JSONObject.toJSONString(UserInfo));
        return ResponseResult.ok("绑定成功");
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
        // 获取当前用户信息
        String userInfo = redisClient.get(getCurTokenId(request));
        UserInfoVo infoVo = JSONObject.parseObject(userInfo,UserInfoVo.class);
        log.debug("当前用户信息：{}",infoVo);
        log.debug("当前用户信息ID：{}",infoVo.getId());
        log.debug("当前用户信息SJ：{}",userService.get(infoVo.getId()));
        return ResponseResult.ok(userService.get(infoVo.getId()));
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

    /**
     * 插入用户信息
     *
     * @param weChatUser 聊天用户
     * @param request    请求
     * @return {@link ResponseResult}
     */
    @PostMapping("/ijianshen")
    @ApiOperation("i健身用户")
    public ResponseResult insertUserInfo(@Valid @RequestBody WeChatUserVO weChatUser, HttpServletRequest request){
        int i = wechatService.addUserInfo(weChatUser);
        if(i>0){
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }

    /**
     * 获取微信用户列表
     *
     * @param sysUserVO VO
     * @param request   请求
     * @return {@link ResponseResult}
     */
    @PostMapping("list/page")
    @ApiOperation("微信用户列表")
    public ResponseResult queryWeChatUser(@RequestBody SysUserVO sysUserVO, HttpServletRequest request){
        Page<User> page = wechatService.findWeChatUserByPage(sysUserVO);
        return ResponseResult.ok(page);
    }

    @GetMapping("/test")
    @ApiOperation("公众号消息")
    @AllowAnonymous
    public ResponseResult test(HttpServletRequest request) {
        List<String> userList = new ArrayList<>();
        userList.add("obOp1s11wNrrTTi4OOqevC-0MhBU");
//        userList.add("obOp1s_lmgFvmAacfg3hs0s2wHVU");
//        userList.add("obOp1s-Sj22VggP-wBYff1KBvnvo");
//        CourseInfo courseInfo = courseInfoService.get(6L);
        CourseInfo courseInfo = courseInfoService.get(142L);
        wechatService.sendWechatMessages(userList,courseInfo,null);
        return ResponseResult.ok();
    }

}
