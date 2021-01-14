package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysUser;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.service.SysUserService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gaorh
 * @desc 后台用户表（管理员、用户）
 * (SysUser)表控制层
 * @date 2021-01-07 17:49:06
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping
@Api(tags = "后台用户")
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @Autowired
    private RedisClient redisClient;

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return {@link ResponseResult<JSONObject>}
     */
    @AllowAnonymous
    @GetMapping("/captcha")
    @ApiOperation("生成验证码")
    public ResponseResult<JSONObject> fetchCaptcha(HttpServletRequest request){
        // png格式验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();
        String key = "HEROCHEER-INSTRUCTOR-"+ verCode;

        // 存入redis并设置过期时间为60秒
        redisClient.set(key.trim(), verCode, 3600);
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
    @AllowAnonymous
    @PostMapping("/sysUser")
    @ApiOperation("用户注册")
    public ResponseResult<SysUser> registerUser(@ApiParam("用户信息") @RequestBody SysUserVO sysUserVO){
        return ResponseResult.ok(sysUserService.addUser(sysUserVO));
    }

    @AllowAnonymous
    @PostMapping("/sysUser/page")
    @ApiOperation("用户列表")
    public ResponseResult<Page<SysUser>> queryUsers(@RequestBody SysUserVO sysUserVO, HttpServletRequest request){
        Page<SysUser> page = sysUserService.findUserByPage(sysUserVO);
        return ResponseResult.ok(page);
    }

    /**
     * 根据userId获取用户信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @AllowAnonymous
    @GetMapping("/sysUser/{id:\\w+}")
    @ApiOperation("获取个人用户信息")
    public ResponseResult<SysUser> fetchUserById(@ApiParam("用户ID") @PathVariable Long id){
        return ResponseResult.ok(sysUserService.get(id));
    }
    /**
     * 编辑用户信息
     *
     * @param sysUserVO 用户签证官
     * @return {@link ResponseResult}
     */
    @AllowAnonymous
    @PutMapping("/sysUser")
    @ApiOperation("编辑用户信息")
    public ResponseResult<SysUser> editUser(@ApiParam("用户信息") @RequestBody SysUserVO sysUserVO){
        return ResponseResult.ok(sysUserService.modifyUser(sysUserVO));
    }
    /**
     * 登录账户
     *
     * @param username 用户名
     * @param password 密码
     * @param verCode  版本的代码
     * @return {@link ResponseResult}
     */
    @PostMapping("/account")
    @AllowAnonymous
    @ApiOperation("用户登入")
    public ResponseResult loginAccount(@ApiParam("用户名") @RequestParam String username,
                                @ApiParam("密码") @RequestParam String password,
                                @ApiParam("验证码") @RequestParam String verCode){
        // 登入流程
        return ResponseResult.ok(sysUserService.login(username,password,verCode));
    }

    /**
     * 更改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return {@link ResponseResult}
     */
    @PutMapping("/password")
    @AllowAnonymous
    @ApiOperation("密码修改")
    public ResponseResult changePassword(@ApiParam("旧密码") @RequestParam String oldPassword,
                                       @ApiParam("新密码") @RequestParam String newPassword,HttpServletRequest request){
        // 修改密码
        sysUserService.modifyPassword(this.getUser(request).getId(),oldPassword,newPassword);
        // sysUserService.modifyPassword(4L,oldPassword,newPassword);
        return ResponseResult.ok();
    }

    /**
     * 退出
     *
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/sysUser")
    @AllowAnonymous
    @ApiOperation("用户退出")
    public ResponseResult logout(HttpServletRequest request){

        // TODO 获取用户信息

        //  TODO 删除缓存中的token
        return ResponseResult.ok();
    }

    /**
     * 禁止用户
     *
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @AllowAnonymous
    @PutMapping("/sysUser/status")
    @ApiOperation("禁用")
    public ResponseResult forbidSysUser(HttpServletRequest request){
        // TODO 获取当前用户信息,更新状态

        //TODO 删除缓存中的token
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
        String redisCode = "HEROCHEER-INSTRUCTOR-"+ verCode.trim();
        // 判断验证码
        if (!redisClient.hasKey(redisCode)) {
            return ResponseResult.fail("验证码不正确");
        }
        return ResponseResult.ok();
    }
}