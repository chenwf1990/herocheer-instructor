package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import com.herocheer.instructor.domain.vo.CourseScheduleCancelVO;
import com.herocheer.instructor.domain.vo.CourseScheduleVO;
import com.herocheer.instructor.service.CourseScheduleService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 课表信息(CourseSchedule)表控制层
 * @date 2021-05-14 15:51:48
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping
@Api(tags = "课表信息")
public class CourseScheduleController extends BaseController {
    @Resource
    private CourseScheduleService courseScheduleService;

    /**
     * 创建课表
     *
     * @param courseSchedule 课程安排
     * @return {@link ResponseResult<CourseSchedule>}
     */
    @PostMapping("/courseSchedule")
    @ApiOperation("新增课表信息")
    public ResponseResult<CourseSchedule> createCourseSchedule(@RequestBody CourseSchedule courseSchedule){
        return ResponseResult.ok().setData(courseScheduleService.insert(courseSchedule));
    }

    /**
     * 批量创建课表
     *
     * @param courseScheduleList 课程安排列表
     * @return {@link ResponseResult<List<CourseSchedule>>}
     */
    @PostMapping("/many/courseSchedules")
    @ApiOperation("批量新增课表信息")
    public ResponseResult<List<CourseSchedule>> batchCreateCourseSchedules(@RequestBody List<CourseSchedule> courseScheduleList){
        return ResponseResult.ok().setData(courseScheduleService.batchAddCourseSchedules(courseScheduleList));
    }


    /**
     * 根据courseId获取课程时间表
     *
     * @param courseId 进程id
     * @param request  请求
     * @return {@link ResponseResult<List<CourseSchedule>>}
     */
    @GetMapping("/courseSchedules/{courseId:\\w+}")
    @ApiOperation("课表信息")
    public ResponseResult<List<CourseScheduleVO>> fetchCourseSchedulesByCourseId(@ApiParam("课程ID") @PathVariable Long courseId, HttpServletRequest request){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseId",courseId);
        return ResponseResult.ok().setData(courseScheduleService.findByLimit(paramMap));
    }


    /**
     * 批量编辑课程时间表
     *
     * @param courseScheduleList 课程安排列表
     * @return {@link ResponseResult<List<CourseSchedule>>}
     */
    @PutMapping("/many/courseSchedules")
    @ApiOperation("批量更新课表信息")
    public ResponseResult<List<CourseSchedule>> batchEditCourseSchedules(@ApiParam("课表信息") @RequestBody List<CourseSchedule> courseScheduleList,HttpServletRequest request){
        return ResponseResult.ok().setData(courseScheduleService.batchupdateCourseSchedules(courseScheduleList));
    }


    /**
     * 取消课表
     *
     * @param courseScheduleCancelVO 课程安排取消签证官
     * @param request                请求
     * @return {@link ResponseResult<CourseSchedule>}
     */
    @PostMapping("/courseSchedules/cancel")
    @ApiOperation("取消课表")
    public ResponseResult<CourseSchedule> cancelCourseSchedulesById(@ApiParam("取消课表信息") @RequestBody CourseScheduleCancelVO courseScheduleCancelVO,HttpServletRequest request){
        return ResponseResult.ok().setData(courseScheduleService.cancelCourseSchedulesById(courseScheduleCancelVO));
    }
}