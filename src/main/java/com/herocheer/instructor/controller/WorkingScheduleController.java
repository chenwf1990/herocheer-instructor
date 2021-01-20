package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.vo.*;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/templateExport")
    @ApiOperation("模板导出")
    public ResponseResult templateExport(@ApiParam("驿站id") @RequestParam Long courierStationId,
                                         @ApiParam("服务时段id") @RequestParam Long serviceTimeId,
                                         HttpServletResponse response){
        workingScheduleService.templateExport(courierStationId,serviceTimeId,response);
        return ResponseResult.ok();
    }


    @PostMapping("/workingScheduleImport")
    @ApiOperation("排班导入")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "file", value = "文件对象", required = true, dataType = "__file"),
            @ApiImplicitParam(name = "courierStationId", value = "驿站id",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "serviceTimeId", value = "服务时段id",dataType = "long",paramType = "query"),
    })
    public ResponseResult workingScheduleImport(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request){
        String courierStationId = request.getParameter("courierStationId");
        String serviceTimeId = request.getParameter("serviceTimeId");
        workingScheduleService.workingScheduleImport(Long.valueOf(courierStationId),Long.valueOf(serviceTimeId),multipartFile);
        return ResponseResult.ok();
    }

    @PostMapping("/getUserWorkingList")
    @ApiOperation("获取当前用户月份排班信息")
    public ResponseResult<List<WorkingUserInfoVo>> getUserWorkingList(@ApiParam("当前月份(yyyy-MM)") @RequestParam(required = false) String monthData,
                                                                      HttpServletRequest request){
        Long userId = getCurUserId(request);
        List<WorkingUserInfoVo> workingUserInfoVos = workingScheduleService.getUserWorkingList(monthData,userId);
        return ResponseResult.ok(workingUserInfoVos);
    }

    @PostMapping("/getTaskInfo")
    @ApiOperation("获取值班任务信息(值班打卡)")
    public ResponseResult<WorkingUserVo> getTaskInfo(@ApiParam("值班人员id") @RequestParam() Long workingScheduleUserId,
                                                     HttpServletRequest request){
        Long userId = getCurUserId(request);
        WorkingUserVo workingUserVo = workingScheduleService.getTaskInfo(workingScheduleUserId,userId);
        return ResponseResult.ok(workingUserVo);
    }
}