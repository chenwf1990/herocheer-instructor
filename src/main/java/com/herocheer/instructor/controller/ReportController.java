package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.service.ReportService;
import com.herocheer.instructor.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}
