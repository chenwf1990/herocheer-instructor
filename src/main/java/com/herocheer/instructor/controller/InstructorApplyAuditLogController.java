package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApplyAuditLog)表控制层
 * @date 2021-01-29 09:02:43
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("")
@Api(tags = "")
public class InstructorApplyAuditLogController extends BaseController{
    @Resource
    private InstructorApplyAuditLogService instructorApplyAuditLogService;

    

}