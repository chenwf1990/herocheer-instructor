package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.vo.CourseStatisVO;
import com.herocheer.instructor.domain.vo.DutyStatisVO;
import com.herocheer.instructor.domain.vo.MatchStatisVO;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.service.ReportService;
import com.herocheer.instructor.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chenwf
 * @desc 报表统计
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/report")
@Api(tags = "报表统计")
public class ReportController {
    @Resource
    private ReportService reportService;

    @PostMapping("/serviceHoursReport")
    @ApiOperation("服务报表统计")
    public ResponseResult<Page<ServiceHoursReportVo>> serviceHoursReport(@RequestBody ServiceHoursQueryVo serviceHoursQueryVo){
        Long beginTime = serviceHoursQueryVo.getBeginTime();
        Long endTime = serviceHoursQueryVo.getEndTime();
        Long curTime = DateUtil.beginOfDay(new Date()).getTime();
        if(beginTime != null && beginTime >= curTime){
            throw new CommonException("开始时间不在服务时长统计范围");
        }
        if(endTime != null && endTime >= curTime){
            throw new CommonException("结束时间不在服务时长统计范围");
        }
        Page<ServiceHoursReportVo> serviceHoursReport = reportService.serviceHoursReport(serviceHoursQueryVo);
        return ResponseResult.ok().setData(serviceHoursReport);
    }

    /**
     * 值班服务时长统计
     *
     * @param request      请求
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link ResponseResult<Page<SysDept>>}
     */
    @PostMapping("/duty/statistics/page")
    @ApiOperation("值班服务时长统计")
    public ResponseResult<Page<DutyStatisVO>> fecthDutyStatisByPage(@ApiParam("值班服务时长") @RequestBody DutyStatisVO dutyStatisVO, HttpServletRequest request){
        return ResponseResult.ok(reportService.findDutyStatisByPage(dutyStatisVO));
    }

    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @param request       请求
     * @return {@link ResponseResult<Page<DutyStatisVO>>}
     */
    @PostMapping("/match/statistics/page")
    @ApiOperation("赛事服务时长统计")
    public ResponseResult<Page<MatchStatisVO>> fecthMatchStatisByPage(@ApiParam("赛事服务时长") @RequestBody MatchStatisVO matchStatisVO, HttpServletRequest request){
        return ResponseResult.ok(reportService.findMatchStatisByPage(matchStatisVO));
    }

    /**
     * 课程服务时长统计
     *
     * @param request        请求
     * @param courseStatisVO 当然统计学的签证官
     * @return {@link ResponseResult<Page<MatchStatisVO>>}
     */
    @PostMapping("/course/statistics/page")
    @ApiOperation("课程服务时长统计")
    public ResponseResult<Page<CourseStatisVO>> fecthCourseStatisByPage(@ApiParam("课程服务时长") @RequestBody CourseStatisVO courseStatisVO, HttpServletRequest request){
        return ResponseResult.ok(reportService.findCourseStatisByPage(courseStatisVO));
    }
}
