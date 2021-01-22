package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.RecruitStateEnums;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author linjf
 * @desc  招募信息主表(ActivityRecruitInfo)表控制层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/activity/recruit")
@Api(tags = "招募信息管理")
public class ActivityRecruitInfoController extends BaseController{
    @Resource
    private ActivityRecruitInfoService activityRecruitInfoService;

    @PostMapping("/queryPage")
    @ApiOperation("招募信息查询")
    public ResponseResult<Page<ActivityRecruitInfo>> queryPageList(@RequestBody ActivityRecruitInfoQueryVo queryVo,HttpServletRequest request){
        Page<ActivityRecruitInfo> page = activityRecruitInfoService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询招募信息详情")
    @AllowAnonymous
    public ResponseResult<ActivityRecruitInfoVo> get(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.ok(activityRecruitInfoService.getActivityRecruitInfo(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增招募信息")
    public ResponseResult add(@RequestBody ActivityRecruitInfoVo activityRecruitInfoVo){
        Integer count=activityRecruitInfoService.addActivityRecruitInfo(activityRecruitInfoVo);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("更新招募信息")
    public ResponseResult update(@RequestBody ActivityRecruitInfoVo activityRecruitInfoVo){
        Integer count=activityRecruitInfoService.updateActivityRecruitInfo(activityRecruitInfoVo);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除招募信息")
    public ResponseResult delete(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.isSuccess(activityRecruitInfoService.deleteActivityRecruitInfo(id));
    }

    @DeleteMapping("/detail/delete")
    @ApiOperation("删除招募信息详情-时段")
    public ResponseResult deleteDetail(@ApiParam("招募信息详情id") @RequestParam Long id){
        return ResponseResult.isSuccess(activityRecruitInfoService.deleteDetail(id));
    }

    @PostMapping("/approval")
    @ApiOperation("招募信息审批")
    public ResponseResult approval(@RequestBody ActivityRecruitApproval activityRecruitApproval){
        Integer count=activityRecruitInfoService.approval(activityRecruitApproval);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/approval/record")
    @ApiOperation("根据id查询审批记录")
    public ResponseResult<List<ActivityRecruitApproval>> approvalRecord(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.ok(activityRecruitInfoService.approvalRecord(id));
    }

    @GetMapping("/hours")
    @ApiOperation("查询活动的招募时段")
    public ResponseResult<List<ActivityRecruitDetailVo>> recruitHours(@ApiParam("招募信息id") @RequestParam Long recruitId,
                                                                      @ApiParam("日期-时间戳")@RequestParam Long dateTime, HttpServletRequest request){
        return ResponseResult.ok(activityRecruitInfoService.getRecruitHours(recruitId,dateTime,getCurUserId(request)));
    }

}