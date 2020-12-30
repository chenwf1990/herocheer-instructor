package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.Test;
import com.herocheer.instructor.service.TestService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试模块")
public class TestController extends BaseController {
    @Resource
    private TestService testService;

    @GetMapping
    @AllowAnonymous
    public ResponseResult test(){
        Test test = testService.get(1L);
        return ResponseResult.ok().setData(test);
    }
}
