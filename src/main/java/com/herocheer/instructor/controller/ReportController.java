package com.herocheer.instructor.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.vo.CourseStatisVO;
import com.herocheer.instructor.domain.vo.DutyStatisVO;
import com.herocheer.instructor.domain.vo.MatchStatisVO;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.domain.vo.ServiceTotalVO;
import com.herocheer.instructor.service.ReportService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @SneakyThrows
    @GetMapping("/duty/statistics/excel")
    @ApiOperation("值班服务时长统计导出")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "courierStationId", value = "所属驿站",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "值班开始日期",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "值班结束日期",dataType = "long",paramType = "query")
    })
    public void dutyStatisticsExcelDownload(String name,Long courierStationId,Long beginTime,Long endTime,HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "值班服务时长统计信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        DutyStatisVO dutyStatisVO = DutyStatisVO.builder().build();
        dutyStatisVO.setName(name);
        dutyStatisVO.setCourierStationId(courierStationId);
        dutyStatisVO.setBeginTime(beginTime);
        dutyStatisVO.setEndTime(endTime);

        List<DutyStatisVO> dutyStatisList = reportService.findDutyStatis(dutyStatisVO);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), DutyStatisVO.class, dutyStatisList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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

    @SneakyThrows
    @GetMapping("/match/statistics/excel")
    @ApiOperation("赛事服务时长统计导出")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "人员姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "activityTitle", value = "赛事名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "服务开始日期",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "服务结束日期",dataType = "long",paramType = "query")
    })
    public void matchStatisticsExcelDownload(String name,String activityTitle,Long beginTime,Long endTime,HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "赛事服务时长统计信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        MatchStatisVO matchStatisVO = MatchStatisVO.builder().build();
        matchStatisVO.setName(name);
        matchStatisVO.setActivityTitle(activityTitle);
        matchStatisVO.setBeginTime(beginTime);
        matchStatisVO.setEndTime(endTime);
        List<MatchStatisVO> matchStatisList = reportService.findMatchStatis(matchStatisVO);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), MatchStatisVO.class, matchStatisList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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

    @SneakyThrows
    @GetMapping("/course/statistics/excel")
    @ApiOperation("课程服务时长统计导出")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lecturerTeacherName", value = "姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "title", value = "课程名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开课开始日期",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "开课结束日期",dataType = "long",paramType = "query")
    })
    public void courseStatisticsExcelDownload(String lecturerTeacherName,String title,Long beginTime,Long endTime,HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "课程服务时长统计信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        CourseStatisVO courseStatisVO = CourseStatisVO.builder().build();
        courseStatisVO.setLecturerTeacherName(lecturerTeacherName);
        courseStatisVO.setTitle(title);
        courseStatisVO.setBeginTime(beginTime);
        courseStatisVO.setEndTime(endTime);
        List<CourseStatisVO> courseStatisList = reportService.findCourseStatis(courseStatisVO);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), CourseStatisVO.class, courseStatisList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    /**
     * 服务时长汇总统计
     *
     * @param serviceTotalVO 服务总签证官
     * @param request        请求
     * @return {@link ResponseResult<Page<ServiceTotalVO>>}
     */
    @PostMapping("/total/statistics/page")
    @ApiOperation("服务时长汇总统计")
    public ResponseResult<Page<ServiceTotalVO>> fecthTotalStatisByPage(@ApiParam("服务时长汇总统计") @RequestBody ServiceTotalVO serviceTotalVO, HttpServletRequest request){
        return ResponseResult.ok(reportService.findTotalStatisByPage(serviceTotalVO));
    }


    @SneakyThrows
    @GetMapping("/total/statistics/excel")
    @ApiOperation("服务时长汇总统计导出")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "guideProject", value = "指导项目",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "certificateGrade", value = "证书等级",dataType = "String",paramType = "query")
    })
    public void statisticsExcelTotalDownload(String name,String guideProject,String certificateGrade,HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "服务时长汇总统计信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        ServiceTotalVO serviceTotalVO =  ServiceTotalVO.builder().build();
        serviceTotalVO.setName(name);
        serviceTotalVO.setGuideProject(guideProject);
        serviceTotalVO.setCertificateGrade(certificateGrade);
        List<ServiceTotalVO> serviceTotalList = reportService.findTotalStatis(serviceTotalVO);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), ServiceTotalVO.class, serviceTotalList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
