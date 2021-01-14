package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenwf
 * @desc  值班签到记录(WorkingSignRecord)表控制层
 * @date 2021-01-12 11:17:13
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/punchCard")
@Api(tags = "打卡管理")
public class WorkingSignRecordController extends BaseController{
    @Resource
    private WorkingSignRecordService workingSignRecordService;

    @GetMapping("/getPunchCardList")
    @ApiOperation("获取打卡信息列表")
    public ResponseResult<List<WorkingSignRecord>> workingScheduls(@ApiParam("值班人员id") Long workingScheduleUserId){
        List<WorkingSignRecord> workingSignRecords = workingSignRecordService.getPunchCardList(workingScheduleUserId);
        return ResponseResult.ok(workingSignRecords);
    }

    @GetMapping("/addWorkingSignRecord")
    @ApiOperation("打卡")
    public ResponseResult addWorkingSignRecord(@RequestBody WorkingSignRecord workingSignRecord, HttpServletRequest request){
        UserEntity userEntity = getUser(request);
        return ResponseResult.isSuccess(workingSignRecordService.addWorkingSignRecord(workingSignRecord,userEntity));
    }

}