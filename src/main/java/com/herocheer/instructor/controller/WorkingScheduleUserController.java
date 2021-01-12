package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表控制层
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/scheduleUser")
@Api(tags = "值班人员")
public class WorkingScheduleUserController extends BaseController{
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    @PostMapping("/queryPageList")
    @ApiOperation("值班人员列表查询")
    @AllowAnonymous
    public ResponseResult<Page<WorkingSchedulsUserVo>> queryPageList(@RequestBody WorkingScheduleUserQueryVo workingScheduleUserQueryVo){
        Page<WorkingSchedulsUserVo> page = workingScheduleUserService.queryPageList(workingScheduleUserQueryVo);
        return ResponseResult.ok(page);
    }

}