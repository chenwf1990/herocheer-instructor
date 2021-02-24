package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourseReservation;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.CourseReservationVo;
import com.herocheer.instructor.service.CourseReservationService;
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
 * @author makejava
 * @desc   课程预约表(CourseReservation)表控制层
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/course/reservation")
@Api(tags = "课程预约")
public class CourseReservationController extends BaseController{
    @Resource
    private CourseReservationService courseReservationService;

    @PostMapping("/add")
    @ApiOperation("课程信息预约")
    public ResponseResult reservation(@RequestBody CourseReservationVo courseReservationVo, HttpServletRequest request){
        Integer count=courseReservationService.reservation(courseReservationVo,getCurUserId(request));
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/cancel")
    @ApiOperation("取消课程预约")
    public ResponseResult cancel(@ApiParam("课程id") @RequestParam Long id){
        return ResponseResult.ok(courseReservationService.cancel(id));
    }

    @PostMapping("/queryPage")
    @ApiOperation("课程信息列表查询")
    public ResponseResult<Page<CourseInfoVo>> queryPageList(@RequestBody CourseInfoQueryVo queryVo, HttpServletRequest request){
        Page<CourseInfoVo> page = courseReservationService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/list")
    @ApiOperation("查询预约信息")
    public ResponseResult<Page<CourseReservation>> getReservationPage(@ApiParam("课程id") @RequestParam Long id,
                                                                      @ApiParam("预约状态(0.已预约1.取消预约)")@RequestParam Integer status,
                                                                      @ApiParam("页码")@RequestParam Integer pageNo,
                                                                      @ApiParam("页数")@RequestParam Integer pageSize){
        return ResponseResult.ok(courseReservationService.getReservationPage(id,status,pageNo,pageSize));
    }
}