package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.SysDictService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表控制层
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("")
@Api(tags = "")
@Slf4j
public class SysDictController extends BaseController {
    @Resource
    private SysDictService sysDictService;


}