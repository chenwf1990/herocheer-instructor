package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.ReservationVO;
import com.herocheer.instructor.domain.vo.SignInfoVO;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/course")
    @ApiOperation("线上课程预约")
    public ResponseResult reservation( @ApiParam("预约信息") @RequestBody List<ReservationVO> reservationList, HttpServletRequest request){
        reservationService.reservation(reservationList,getCurUserId(request));
        return ResponseResult.ok();
    }
    @PostMapping("/web/course")
    @ApiOperation("后端预约")
    public ResponseResult webReservation(@RequestBody Reservation reservation){
        Integer count=reservationService.webReservation(reservation);
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

    /**
     * 创建签名信息
     *
     * @param reservationList 预订单
     * @param request         请求
     * @return {@link ResponseResult}
     */
    @PostMapping("/sign/info")
    @ApiOperation("线下预约签到")
    public ResponseResult createSignInfo(@ApiParam("预约信息") @Valid @RequestBody List<ReservationVO> reservationList, HttpServletRequest request){
        // 返回签到时间
        Long signTime = reservationService.addSignInfo(reservationList,getCurUserId(request));
        return ResponseResult.ok(signTime);
    }


    /**
     * 线上签到
     *
     * @param courseId 进程id
     * @param request  请求
     * @return {@link ResponseResult}
     */
    @GetMapping("/online/sign/info")
    @ApiOperation("线上签到")
    public ResponseResult createOnlineSignInfo(@ApiParam("课程ID") Long courseId, HttpServletRequest request){
        // 返回签到时间
        Long signTime = reservationService.addOnlineSignInfo(courseId,getCurUserId(request));
        return ResponseResult.ok(signTime);
    }
    /**
     * 签到信息列表
     *
     * @param signInfoVO VO
     * @param request 请求
     * @return {@link ResponseResult<Page<Reservation>>}
     */
    @PostMapping("/sign/page")
    @ApiOperation("签到列表")
    public ResponseResult<Page<Reservation>> querySignInfoByPage(@RequestBody SignInfoVO signInfoVO, HttpServletRequest request){
        Page<Reservation> page = reservationService.findSignInfoByPage(signInfoVO);
        return ResponseResult.ok(page);
    }

    /**
     * 根据当前用户ID获取预约信息
     *
     * @param courseId 进程id
     * @return {@link ResponseResult<List<Reservation>>}
     */
    @GetMapping("/info/{courseId:\\w+}")
    @ApiOperation("已参与人员")
    public ResponseResult<List<Reservation>> fecthReservationBycurrentUserId(@ApiParam("课程ID") @PathVariable Long courseId, HttpServletRequest request){
        return ResponseResult.ok(reservationService.findReservationByCurrentUserId(courseId,1L));
    }
}