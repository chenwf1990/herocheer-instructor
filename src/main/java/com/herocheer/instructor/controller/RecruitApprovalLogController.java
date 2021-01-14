package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.RecruitApprovalLogService;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author makejava
 * @desc  招募审批日志 (RecruitApprovalLog)表控制层
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("")
@Api(tags = "")
public class RecruitApprovalLogController extends BaseController{
    @Resource
    private RecruitApprovalLogService recruitApprovalLogService;

    

}