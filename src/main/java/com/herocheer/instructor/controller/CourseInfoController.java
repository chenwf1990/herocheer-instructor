package com.herocheer.instructor.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.aspect.SysMessageEvent;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.enums.InsuranceConst;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.CourseInfoService;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.instructor.utils.SpringUtil;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表控制层
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping("/course")
@Api(tags = "课程管理")
public class CourseInfoController extends BaseController{
    @Resource
    private CourseInfoService courseInfoService;

    @Autowired
    private SysMessageService sysMessageService;


    @PostMapping("/queryPage")
    @ApiOperation("课程信息列表查询")
    public ResponseResult<Page<CourseInfo>> queryPageList(@RequestBody CourseInfoQueryVo queryVo, HttpServletRequest request){
        Page<CourseInfo> page = courseInfoService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/withdraw")
    @ApiOperation("课程撤回")
    public ResponseResult withdraw(@ApiParam("课程id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.withdraw(id));
    }
    @GetMapping("/revoke")
    @ApiOperation("取消课程")
    public ResponseResult revoke(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.revoke(id));
    }

    /**
     *
     * @param id      id
     * @param request 请求
     * @param flag
     * @return {@link ResponseResult<CourseInfo>}
     */
    @GetMapping("/get")
    @ApiOperation("根据id查询课程详情")
    public ResponseResult<CourseInfo> get(@ApiParam("课程信息id") @RequestParam Long id,@ApiParam("扫二维码标识") @RequestParam(value="flag",required=false) String flag,HttpServletRequest request){
        return ResponseResult.ok(courseInfoService.findCourseInfoById(id,flag,getCurUserId(request)));
    }

    @GetMapping("/approval/record")
    @ApiOperation("根据id查询课程审批记录")
    public ResponseResult<List<CourseApproval>> approvalRecord(@ApiParam("课程信息id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.approvalRecord(id));
    }

    /**
     * 创建课程信息
     *
     * @param courseInfoVO 课程信息签证官
     * @return {@link ResponseResult<CourseInfoVo>}
     */
    @PostMapping("/add")
    @ApiOperation("新增课程信息")
    public ResponseResult<CourseInfoVo> createCourseInfo(@RequestBody CourseInfoVo courseInfoVO){
        courseInfoVO.setSignNumber(0);
        courseInfoService.addCourseInfo(courseInfoVO);
        // 采集系统消息
        SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.COURSE_CHECK.getText(),SysMessageEnums.COURSE_CHECK.getType(),SysMessageEnums.COURSE_CHECK.getCode(),courseInfoVO.getId())));
        return ResponseResult.ok(courseInfoVO);
    }

    /**
     * 更新课程信息
     *
     * @param courseInfoVO 课程信息签证官
     * @return {@link ResponseResult}
     */
    @PostMapping("/update")
    @ApiOperation("更新课程信息")
    public ResponseResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVO){
        courseInfoService.updateCourseInfo(courseInfoVO);
        // 同步系统消息状态(不区别审核通过和驳回)
        sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.COURSE_CHECK.getCode()), courseInfoVO.getId(),false,false);
        return ResponseResult.ok();
    }

    @PostMapping("/approval")
    @ApiOperation("课程审批")
    public ResponseResult approval(@RequestBody CourseApproval courseApproval,HttpServletRequest request){
        Integer count=courseInfoService.approval(courseApproval,getUser(request));

        // 同步系统消息状态(不区别审核通过和驳回)
        sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.COURSE_CHECK.getCode()), courseApproval.getCourseId(),true,true);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除课程信息")
    public ResponseResult delete(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.isSuccess(courseInfoService.delete(id));
    }


    /**
     * 获取二维码
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/QrCode")
    @ApiOperation("生产二维码")
    public void fetchQrCode(@ApiParam("课程id") @RequestParam Long id, HttpServletResponse response) throws IOException {
        String url = StrUtil.format(InsuranceConst.QRCODE_URL,id);

        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(0);
        // 高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        // logo
        log.debug("Logo路径：{}", new File("imgs"+File.separator+"logo.png").getCanonicalPath());
        config.setImg(new File("imgs"+File.separator+"logo.png").getCanonicalPath());
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(url, config, "PNG",response.getOutputStream());
    }


    /**
     * 培训任务
     *
     * @param queryVo 查询签证官
     * @param request 请求
     * @return {@link ResponseResult<Page<CourseInfo>>}
     */
    @PostMapping("/task/page")
    @ApiOperation("培训任务")
    public ResponseResult<Page<CourseInfo>> fetchtaskByPage(@RequestBody CourseInfoQueryVo queryVo, HttpServletRequest request){
        Page<CourseInfo> page = courseInfoService.findtaskByPage(queryVo,getUser(request));
        return ResponseResult.ok(page);
    }
}