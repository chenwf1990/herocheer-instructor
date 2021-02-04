
package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.AreaPermissionVO;
import com.herocheer.instructor.domain.vo.MemberVO;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.service.UserService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author gaorh
 * @desc 微信用户、后台用户、后台管理员(User)表控制层
 * @date 2021-01-18 15:45:22
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping
@Api(tags = "系统用户")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisClient redisClient;

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return {@link ResponseResult < JSONObject >}
     */
    @GetMapping("/captcha")
    @ApiOperation("生成验证码")
    public ResponseResult<JSONObject> fetchCaptcha(HttpServletRequest request){
        // png格式验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();
        String key = "HEROCHEER-INSTRUCTOR-"+ verCode;

        // 存入redis并设置过期时间为60秒
        redisClient.set(key.trim(), verCode, 1800);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("image", specCaptcha.toBase64());
        // 将key和base64返回给前端
        return ResponseResult.ok(jsonObject);
    }

    /**
     * 注册用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link ResponseResult}
     */
    @PostMapping("/user")
    @ApiOperation("用户注册")
    public ResponseResult<User> registerUser(@ApiParam("用户信息") @Valid @RequestBody SysUserVO sysUserVO){
        return ResponseResult.ok(userService.addUser(sysUserVO));
    }

    /**
     * 查询用户(分页)
     *
     * @param sysUserVO VO
     * @param request   请求
     * @return {@link ResponseResult<Page<User>>}
     */
    @PostMapping("/user/page")
    @ApiOperation("用户列表")
    public ResponseResult<Page<User>> queryUsers(@RequestBody SysUserVO sysUserVO, HttpServletRequest request){
        Page<User> page = userService.findUserByPage(sysUserVO);
        return ResponseResult.ok(page);
    }

    /**
     * 根据userId获取用户信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/user/{id:\\w+}")
    @ApiOperation("个人信息")
    public ResponseResult<SysUserVO> fetchUserById(@ApiParam("用户ID") @PathVariable Long id){
        return ResponseResult.ok(userService.findUserById(id));
    }

    /**
     * 通过openId获取微信用户
     *
     * @param openId 开放id
     * @return {@link ResponseResult<User>}
     */
    @AllowAnonymous
    @GetMapping("/weChatUser/{openId:\\w+}")
    @ApiOperation("获取微信用户信息(openid)")
    public ResponseResult<User> fetchUserByOpenId(@ApiParam("用户ID") @PathVariable String openId){
        return ResponseResult.ok(userService.findUserByOpenId(openId));
    }

    /**
     * 通过电话获取用户
     *
     * @param phone 电话
     * @return {@link ResponseResult<User>}
     */
    @AllowAnonymous
    @GetMapping("/weChatUser/{phone:\\w+}")
    @ApiOperation("获取微信用户信息(phone)")
    public ResponseResult<User> fetchUserByPhone(@ApiParam("电话号码") @PathVariable String phone){
        return ResponseResult.ok(userService.findUserByPhone(phone));
    }

    /**
     * 新增微信用户
     *
     * @param weChatUserVO VO
     * @return {@link ResponseResult<User>}
     */
    @AllowAnonymous
    @PostMapping("/weChatUser")
    @ApiOperation("新增微信用户")
    public ResponseResult<User> createWeChatUser(@ApiParam("微信用户") @RequestBody WeChatUserVO weChatUserVO){
        return ResponseResult.ok(userService.addWeChatUser(weChatUserVO));
    }

    /**
     * 编辑微信用户
     *
     * @param weChatUserVO VO
     * @return {@link ResponseResult<User>}
     */
    @AllowAnonymous
    @PutMapping("/weChatUser")
    @ApiOperation("编辑微信用户")
    public ResponseResult<User> editWeChatUser(@ApiParam("微信用户") @RequestBody WeChatUserVO weChatUserVO){
        return ResponseResult.ok(userService.modifyWeChatUser(weChatUserVO));
    }
    /**
     * 编辑用户信息
     *
     * @param sysUserVO 用户签证官
     * @return {@link ResponseResult}
     */
    @AllowAnonymous
    @PutMapping("/user")
    @ApiOperation("编辑用户信息")
    public ResponseResult<User> editUser(@ApiParam("用户信息") @Valid @RequestBody SysUserVO sysUserVO){
        return ResponseResult.ok(userService.modifyUser(sysUserVO));
    }

    /**
     * 登录账户
     *
     * @param account  账号
     * @param password 密码
     * @return {@link ResponseResult<String>}
     */
    @GetMapping("/account")
    @AllowAnonymous
    @ApiOperation("用户登入")
    public ResponseResult<String> loginAccount(@ApiParam("账号") @RequestParam String account,
                                       @ApiParam("密码") @RequestParam String password){
        // 登入流程
        return ResponseResult.ok(userService.login(account,password));
    }

    /**
     * 更改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return {@link ResponseResult}
     */
    @GetMapping("/password")
    @ApiOperation("密码修改")
    public ResponseResult changePassword(@ApiParam("旧密码") @RequestParam String oldPassword,
                                         @ApiParam("新密码") @RequestParam String newPassword,HttpServletRequest request){
        // 获取用户信息
        UserEntity currentUser = this.getUser(request);
        // 修改密码
        userService.modifyPassword(currentUser.getId(),oldPassword,newPassword);
        return ResponseResult.ok();
    }

    /**
     * 重置密码
     *
     * @param id          id
     * @param request     请求
     * @return {@link ResponseResult}
     */
    @PutMapping("/password/{id:\\w+}")
    @AllowAnonymous
    @ApiOperation("密码重置")
    public ResponseResult resetPassword(@ApiParam("用户ID") @PathVariable Long id, HttpServletRequest request){
        // 重置密码为：123456
        return userService.resetPassword(id);
    }


    /**
     * 退出
     *
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/user")
    @ApiOperation("用户退出")
    public ResponseResult logout(HttpServletRequest request){
        // 获取当前用户信息
        UserEntity currentUser = this.getUser(request);
        if(!ObjectUtils.isEmpty(currentUser) && StringUtils.hasText(currentUser.getToken())){
            //  删除缓存中的token
            redisClient.delete(currentUser.getToken());
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }

    /**
     * 禁止用户
     *
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @PutMapping("/user/status")
    @ApiOperation("禁用用户")
    public ResponseResult forbidSysUser(HttpServletRequest request){
        // TODO 获取当前用户信息,更新状态

        //TODO 删除缓存中的token
        return ResponseResult.ok();
    }

    /**
     * 删除系统用户
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/user/{id:\\w+}")
    @ApiOperation("删除用户")
    public ResponseResult dropSysUserById(@ApiParam("用户ID") @PathVariable Long id, HttpServletRequest request){
        // 逻辑删除
        userService.removeSysUserById(id);
        return ResponseResult.ok();
    }
    /**
     * 验证验证码
     *
     * @param verCode 版本的代码
     * @return {@link ResponseResult}
     */
    @PostMapping("/captcha")
    @AllowAnonymous
    @ApiOperation("验证码验证")
    public ResponseResult vaildCaptcha(@ApiParam("验证码") @RequestParam String verCode){
        String redisCode = "INSTRUCTOR-CODE-KEY"+ verCode.trim();
        // 判断验证码
        if (!redisClient.hasKey(redisCode)) {
            return ResponseResult.fail("验证码不正确");
        }
        return ResponseResult.ok();
    }

    /**
     * 获取用户信息
     *
     * @param request 请求
     * @return {@link ResponseResult<List<SysUserVO>>}
     */
    @GetMapping("/user/name")
    @ApiOperation("用户名称")
    @AllowAnonymous
    public ResponseResult<List<MemberVO>> fetchUser(HttpServletRequest request){
        return ResponseResult.ok( userService.findUser());
    }

    /**
     * 根据userType获取用户
     *
     * @param request 请求
     * @return {@link ResponseResult<List<SysUserVO>>}
     */
    @GetMapping("/user/name/{userType}")
    @ApiOperation("根据userType返回用户")
    @AllowAnonymous
    public ResponseResult<List<MemberVO>> fetchUserByuserType(@ApiParam("用户类型") @PathVariable String userType,HttpServletRequest request){
        return ResponseResult.ok(userService.findUserByUserType(userType));
    }

    /**
     * 获取当前用户获取数据权限
     *
     * @param request 请求
     * @return {@link ResponseResult<AreaPermissionVO>}
     */
    @GetMapping("/data/permission")
    @ApiOperation("当前用户获取数据权限")
    @AllowAnonymous
    public ResponseResult<AreaPermissionVO> fetchAreaByCurrentUser(HttpServletRequest request){
        return ResponseResult.ok(userService.findAreaByCurrentUser(this.getUser(request).getId()));
    }
}