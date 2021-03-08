package com.herocheer.instructor.controller;

import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ActivityRecruitApproval;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.ApplicationListVo;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Resource
    private RedisClient redisClient;

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

    @GetMapping("/withdraw")
    @ApiOperation("招募信息撤回")
    public ResponseResult withdraw(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.ok(activityRecruitInfoService.withdraw(id));
    }

    @GetMapping("/isPublic")
    @ApiOperation("设置招募信息是否公开")
    public ResponseResult isPublic(@ApiParam("招募信息id") @RequestParam Long id,
                                   @ApiParam("是否公开(0.公开1.不公开)") @RequestParam Integer isPublic){
        return ResponseResult.ok(activityRecruitInfoService.isPublic(id,isPublic));
    }

    @PostMapping("/add")
    @ApiOperation("新增招募信息")
    public ResponseResult add(@RequestBody ActivityRecruitInfoVo activityRecruitInfoVo,
                              HttpServletRequest request){
        reqLimitByUserId(request,"ActivityRecruitInfo_add",1);
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
    public ResponseResult approval(HttpServletRequest request,@RequestBody ActivityRecruitApproval activityRecruitApproval){
        reqLimitByUserId(request,"ActivityRecruitInfo_approval",1);
        Integer count=activityRecruitInfoService.approval(activityRecruitApproval,getUser(request));
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
                                                                      @ApiParam("日期-时间戳")@RequestParam Long dateTime,
                                                                      HttpServletRequest request){
        return ResponseResult.ok(activityRecruitInfoService.getRecruitHours(recruitId,dateTime,getCurUserId(request)));
    }

    @GetMapping("/queryApplicationPage")
    @ApiOperation("公众号-查询信息审批列表")
    public ResponseResult<Page<ApplicationListVo>> queryApplicationPage(@ApiParam("类型(1.待审批2.已审批)") @RequestParam Integer type,
                                                                        @ApiParam("页码") @RequestParam Integer pageNo,
                                                                        @ApiParam("页数") @RequestParam Integer pageSize,
                                                                        HttpServletRequest request){
        return ResponseResult.ok(activityRecruitInfoService.queryApplicationPage(type,pageNo,pageSize,getCurUserId(request)));
    }

    @GetMapping("/getApplicationCount")
    @ApiOperation("公众号-查询信息审批统计数")
    public ResponseResult<Integer> getApplicationCount(@ApiParam("类型(1.待审批2.已审批)") @RequestParam Integer type,
                                                                  HttpServletRequest request){
        return ResponseResult.ok(activityRecruitInfoService.getApplicationCount(type,getCurUserId(request)));
    }



}