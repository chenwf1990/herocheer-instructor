package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.MatchSignRecordVo;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult<List<WorkingSignRecord>> getPunchCardList(@ApiParam("值班人员id") @RequestParam Long workingScheduleUserId){
        List<WorkingSignRecord> workingSignRecords = workingSignRecordService.getPunchCardList(workingScheduleUserId);
        return ResponseResult.ok(workingSignRecords);
    }

    @PostMapping("/addWorkingSignRecord")
    @ApiOperation("打卡")
    public ResponseResult addWorkingSignRecord(@RequestBody WorkingSignRecord workingSignRecord, HttpServletRequest request){
        UserEntity userEntity = getUser(request);
        return ResponseResult.isSuccess(workingSignRecordService.addWorkingSignRecord(workingSignRecord,userEntity));
    }

    @GetMapping("/matchSignRecord")
    @ApiOperation("获取赛事打卡记录")
    public ResponseResult<Page<MatchSignRecordVo>> queryMatchSignRecord(@ApiParam("活动id") @RequestParam Long activityId,
                                                                        @ApiParam("人员名称") @RequestParam String userName,
                                                                        @ApiParam("页数") @RequestParam Integer pageSize,
                                                                        @ApiParam("页码") @RequestParam Integer pageNo){
        Page<MatchSignRecordVo> page = workingSignRecordService.queryMatchSignRecord(pageNo,pageSize,activityId,userName);
        return ResponseResult.ok(page);
    }

}