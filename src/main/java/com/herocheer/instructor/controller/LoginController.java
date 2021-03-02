package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.LoginService;
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
//@Api(tags = "登录api")
public class LoginController {
    @Resource
    private LoginService loginService;
}
