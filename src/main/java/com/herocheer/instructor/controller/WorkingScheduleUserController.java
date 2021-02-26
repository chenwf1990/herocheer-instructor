package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.vo.ReservationInfoQueryVo;
import com.herocheer.instructor.domain.vo.ReservationInfoVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public ResponseResult<Page<WorkingSchedulsUserVo>> queryPageList(@RequestBody WorkingScheduleUserQueryVo workingScheduleUserQueryVo,
                                                                     HttpServletRequest request){
        Page<WorkingSchedulsUserVo> page = workingScheduleUserService.queryPageList(workingScheduleUserQueryVo,getUser(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/approval")
    @ApiOperation("值班审核")
    public ResponseResult approval(@ApiParam("值班人员id") @RequestParam Long workingScheduleUserId,
                                   @ApiParam("审批结果 1按照人员签到签退时间进行统计 2按照活动设定时间进行统计 3按实际情况填写时长")
                                   @RequestParam int approvalType,
                                   @ApiParam("审批意见") @RequestParam(required = false) String approvalIdea,
                                   @ApiParam("实得时长") @RequestParam int actualServiceTime,
                                   HttpServletRequest request){
        return ResponseResult.isSuccess(workingScheduleUserService.approval(workingScheduleUserId,approvalType,approvalIdea,getUser(request),actualServiceTime));
    }

    @PostMapping("/queryReservationInfoPage")
    @ApiOperation("查询预约记录或者服务记录")
    public ResponseResult<Page<ReservationInfoVo>> queryReservationInfoPage(@RequestBody ReservationInfoQueryVo queryVo,
                                                                            HttpServletRequest request){
        Page<ReservationInfoVo> page = workingScheduleUserService.findReservationInfoPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }
}