package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.InstructorApplyService;
import com.herocheer.instructor.service.SysMessageService;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApply)表控制层
 * @date 2021-01-29 08:50:36
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/instructor/apply")
@Api(tags = "指导员申请")
@Slf4j
public class InstructorApplyController extends BaseController{
    @Resource
    private InstructorApplyService instructorApplyService;

    @Autowired
    private SysMessageService sysMessageService;

    @PostMapping("/queryPageList")
    @ApiOperation("指导员申请单列表查询")
    public ResponseResult<Page<InstructorApply>> queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<InstructorApply> page = instructorApplyService.queryPageList(instructorQueryVo);
        return ResponseResult.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("指导员申请")
    public ResponseResult add(@RequestBody InstructorApply instructorApply, HttpServletRequest request){
        UserEntity entity = getUser(request);
        instructorApplyService.addInstructorApply(instructorApply,entity);
        return ResponseResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑指导员申请单")
    public ResponseResult update(@RequestBody InstructorApply instructorApply){
        return ResponseResult.isSuccess(instructorApplyService.updateInstructor(instructorApply));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除指导员申请单")
    public ResponseResult delete(@ApiParam("指导员id") @RequestParam Long id){
        return ResponseResult.isSuccess(instructorApplyService.delete(id));
    }

    @GetMapping("/get")
    @ApiOperation("根据申请单id查询指导员")
    public ResponseResult<InstructorApply> get(@ApiParam("指导员申请单id") @RequestParam Long id){

        return ResponseResult.ok(instructorApplyService.get(id));
    }

    @GetMapping("/approval")
    @ApiOperation("指导员申请单审批")
    public ResponseResult approval(@ApiParam("指导员id") @RequestParam Long id,
                                   @ApiParam("审核状态 0待审核1审核通过2审核驳回") @RequestParam int auditState,
                                   @ApiParam("审核意见") @RequestParam(required = false) String auditIdea,
                                   HttpServletRequest request){
        instructorApplyService.approval(id,auditState,auditIdea,getCurUserId(request));

        // 同步系统消息状态
        sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.INSTRUCTOR_AUTH.getCode()), id,true,true);
        return ResponseResult.ok();
    }

    @GetMapping("/getApprovalLog")
    @ApiOperation("获取审批日志列表信息")
    public ResponseResult getApprovalLog(@ApiParam("申请单id") @RequestParam Long applyId){
        List<InstructorApplyAuditLog> logs = instructorApplyService.getApprovalLog(applyId);
        return ResponseResult.ok(logs);
    }


    @GetMapping("/getAuthInfo")
    @ApiOperation("获取认证信息")
    public ResponseResult<List<InstructorApply>> getAuthInfo(@ApiParam("指导员id") @RequestParam(required = false) Long instructorId,
                                                             HttpServletRequest request){
        UserEntity entity = getUser(request);
        String openId = entity.getOtherId();
        log.info("指导员认证信息：{}", JSONObject.toJSONString(entity));
        List<InstructorApply> applies = instructorApplyService.getAuthInfo(openId,instructorId);
        return ResponseResult.ok(applies);
    }
}