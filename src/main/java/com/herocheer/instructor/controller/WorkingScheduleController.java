package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingVo;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表控制层
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/workingScheduls")
@Api(tags = "排班管理")
public class WorkingScheduleController extends BaseController{
    @Resource
    private WorkingScheduleService workingScheduleService;

    @PostMapping("/queryPageList")
    @ApiOperation("排班列表查询")
    @AllowAnonymous
    public ResponseResult<Page<WorkingScheduleListVo>> queryPageList(@RequestBody WorkingScheduleQueryVo workingScheduleQueryVo){
        Page<WorkingScheduleListVo> page = workingScheduleService.queryPageList(workingScheduleQueryVo);
        return ResponseResult.ok(page);
    }

    @PostMapping
    @ApiOperation("排班")
    public ResponseResult workingScheduls(@RequestBody List<WorkingVo> workingVos){
        workingScheduleService.addWorkingScheduls(workingVos);
        return ResponseResult.ok();
    }

    @GetMapping("/getWorkingScheduls")
    @ApiOperation("根据id获取排班信息")
    public ResponseResult<WorkingVo> getWorkingScheduls(@RequestParam Long id){

        return ResponseResult.ok(workingScheduleService.getWorkingScheduls(id));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除排班信息")
    public ResponseResult delete(@RequestParam Long id){
        WorkingSchedule schedule = workingScheduleService.get(id);
        if(schedule.getScheduleTime() < System.currentTimeMillis()){
            throw new CommonException("值班日期中不能删除");
        }
        return ResponseResult.ok(workingScheduleService.delete(id));
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation("批量删除排班信息")
    public ResponseResult batchDelete(@ApiParam("排班id，多个逗号隔开") @RequestParam String ids){
        return ResponseResult.isSuccess(workingScheduleService.batchDelete(ids));
    }

    @PostMapping("/updateWorkingScheduls")
    @ApiOperation("编辑排班信息")
    public ResponseResult updateWorkingScheduls(@RequestBody WorkingVo workingVo){
        workingScheduleService.updateWorkingScheduls(workingVo);
        return ResponseResult.ok();
    }

}