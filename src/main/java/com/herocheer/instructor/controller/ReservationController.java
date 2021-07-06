package com.herocheer.instructor.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.ReservationListVO;
import com.herocheer.instructor.domain.vo.ReservationMemberInfoVO;
import com.herocheer.instructor.domain.vo.ReservationMemberVO;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.SignInfoVO;
import com.herocheer.instructor.service.ReservationService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation("线上预约")
    public ResponseResult<Reservation> reservation(@ApiParam("预约信息") @RequestBody ReservationMemberVO reservationMemberVO, HttpServletRequest request){
        return ResponseResult.ok(reservationService.reservation(reservationMemberVO,getCurUserId(request)));
    }

    @PostMapping("/web/course")
    @ApiOperation("老年人预约")
    public ResponseResult webReservation(@RequestBody Reservation reservation){
        Integer count = reservationService.webReservation(reservation);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/cancel")
    @ApiOperation("取消预约")
    public ResponseResult cancel(@ApiParam("预约id") @RequestParam Long id){
        reservationService.cancel(id);
        return ResponseResult.ok();
    }


    @PostMapping("/queryPage")
    @ApiOperation("我的预约列表")
    public ResponseResult<Page<ReservationListVO>> queryPageList(@RequestBody ReservationQueryVo queryVo, HttpServletRequest request){
        Page<ReservationListVO> page = reservationService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @SneakyThrows
    @GetMapping("/member/excel")
    @ApiOperation("导出预约信息")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "identityNumber", value = "身份证号",dataType = "Sting",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 (0.已预约 1.取消预约 2.活动撤销 3.课表撤销)",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "queryType", value = "查询类型",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "relevanceId", value = "课程ID",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 (1.驿站招募 2.赛事招募 3.课程培训)",dataType = "int",paramType = "query")
    })
    public void exportToExcel(String name,String identityNumber, Integer status, Integer queryType, Long relevanceId, Integer type,  HttpServletRequest request, HttpServletResponse response){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "预约人员信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        ReservationQueryVo queryVo = ReservationQueryVo.builder().build();
        queryVo.setName(name);
        queryVo.setIdentityNumber(identityNumber);
        queryVo.setStatus(status);
        queryVo.setQueryType(queryType);
        queryVo.setRelevanceId(relevanceId);
        queryVo.setType(type);
        List<ReservationListVO> reservationList = reservationService.findReservationInfo(queryVo);

        // 处理数据
        reservationList.forEach(e->{
            e.setExcelCreatedTime(DateUtil.timeStamp2DateTime(e.getCreatedTime()));
            e.setPhone(AesUtil.decrypt(e.getPhone()));
            // 身份证号
            if(StringUtils.isNotBlank(e.getIdentityNumber())){
                StringBuilder str = new StringBuilder();
                String[] cardNoArr = e.getIdentityNumber().split(",");
                for (int i = 0; i <cardNoArr.length ; i++) {
                    str.append(AesUtil.decrypt(cardNoArr[i]));
                    if(i < cardNoArr.length-1){
                        str.append(",");
                    }
                }
                e.setExcelIdentityNumber(str.toString());
            }
        });

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("预约人员信息","预约人员"), ReservationListVO.class, reservationList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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
     * @param reservationMemberVO 预订单
     * @param request         请求
     * @return {@link ResponseResult}
     */
    @PostMapping("/sign/info")
    @ApiOperation("线下预约及签到")
    public ResponseResult createSignInfo(@ApiParam("预约信息") @RequestBody ReservationMemberVO reservationMemberVO, HttpServletRequest request){
        // 返回签到时间
        Long signTime = reservationService.addSignInfo(reservationMemberVO,getCurUserId(request));
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
    public ResponseResult createOnlineSignInfo(@ApiParam("课程ID") @RequestParam Long courseId, HttpServletRequest request){
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
    public ResponseResult<Page<ReservationListVO>> querySignInfoByPage(@RequestBody SignInfoVO signInfoVO, HttpServletRequest request){
        Page<ReservationListVO> page = reservationService.findSignInfoByPage(signInfoVO);
        return ResponseResult.ok(page);
    }

    /**
     * 根据当前用户ID获取预约信息
     *
     * @param relevanceId 进程id
     * @return {@link ResponseResult<List<Reservation>>}
     */
    @GetMapping("/info/{relevanceId:\\w+}")
    @ApiOperation("已参与人员")
    public ResponseResult<List<ReservationMemberInfoVO>> fecthReservationBycurrentUserId(@ApiParam("预约ID") @PathVariable Long relevanceId, HttpServletRequest request){
        return ResponseResult.ok(reservationService.findReservationByCurrentUserId(relevanceId,getCurUserId(request)));
    }
}