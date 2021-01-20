package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ActivityRecord;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.ActivityReplaceCard;
import com.herocheer.instructor.domain.entity.ActivitySignRecord;
import com.herocheer.instructor.domain.vo.ActivityRecordApprovalVo;
import com.herocheer.instructor.domain.vo.ActivityRecordQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecordVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.service.ActivityRecordService;
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
 * @author makejava
 * @desc  活动记录表(ActivityRecord)表控制层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/activity/record")
@Api(tags = "活动记录管理")
public class ActivityRecordController extends BaseController{
    @Resource
    private ActivityRecordService activityRecordService;



    @PostMapping("/queryPage")
    @ApiOperation("活动记录查询")
    @AllowAnonymous
    public ResponseResult<Page<ActivityRecordVo>> queryPageList(@RequestBody ActivityRecordQueryVo queryVo){
        Page<ActivityRecordVo> page = activityRecordService.queryPage(queryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/sign/record")
    @ApiOperation("根据活动记录id查询打卡记录")
    @AllowAnonymous
    public ResponseResult<List<ActivitySignRecord>> signRecord(@ApiParam("活动记录id") @RequestParam Long id){
        return ResponseResult.ok(activityRecordService.getSignByRecordId(id));
    }

    @GetMapping("/replace/card")
    @ApiOperation("根据活动记录id查询补卡记录")
    @AllowAnonymous
    public ResponseResult<List<ActivityReplaceCard>> replaceCard(@ApiParam("活动记录id") @RequestParam Long id){
        return ResponseResult.ok(activityRecordService.getReplaceCardByRecordId(id));
    }

    @PostMapping("/approval")
    @ApiOperation("活动记录审批")
    public ResponseResult approval(@RequestBody ActivityRecordApprovalVo approvalVo, HttpServletRequest request){
        Long count=activityRecordService.approval(approvalVo,getUser(request));
        return ResponseResult.isSuccess(count);
    }

}