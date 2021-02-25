package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.CourseReservationVo;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.service.ReservationService;
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
 * @desc  预约记录(Reservation)表控制层
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/reservation")
@Api(tags = "预约管理")
public class ReservationController extends BaseController{
    @Resource
    private ReservationService reservationService;

    @PostMapping("/add")
    @ApiOperation("课程信息预约")
    public ResponseResult reservation(@RequestBody CourseReservationVo courseReservationVo, HttpServletRequest request){
        Integer count=reservationService.reservation(courseReservationVo,getCurUserId(request));
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/cancel")
    @ApiOperation("取消预约")
    public ResponseResult cancel(@ApiParam("预约id") @RequestParam Long id){
        return ResponseResult.ok(reservationService.cancel(id));
    }

    @PostMapping("/queryPage")
    @ApiOperation("我的预约列表")
    public ResponseResult<Page<Reservation>> queryPageList(@RequestBody ReservationQueryVo queryVo, HttpServletRequest request){
        Page<Reservation> page = reservationService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/activity/info")
    @ApiOperation("获取招募预约详情")
    public ResponseResult<ActivityRecruitInfoVo> activity(@ApiParam("预约id") @RequestParam Long id){
        return ResponseResult.ok(reservationService.getActivity(id));
    }

    @GetMapping("/courier/info")
    @ApiOperation("获取课程预约详情")
    public ResponseResult<CourseInfoVo> courier(@ApiParam("预约id") @RequestParam Long id){
        return ResponseResult.ok(reservationService.getCourse(id));
    }

}