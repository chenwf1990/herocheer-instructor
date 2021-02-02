package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.InstructorApplyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class InstructorApplyController extends BaseController{
    @Resource
    private InstructorApplyService instructorApplyService;

    @PostMapping("/queryPageList")
    @ApiOperation("指导员申请单列表查询")
    public ResponseResult<Page<InstructorApply>> queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<InstructorApply> page = instructorApplyService.queryPageList(instructorQueryVo);
        return ResponseResult.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("指导员申请")
    public ResponseResult add(@RequestBody InstructorApply instructorApply,
                              HttpServletRequest request){
        return ResponseResult.isSuccess(instructorApplyService.addInstructorApply(instructorApply,getCurUserId(request)));
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

    @GetMapping("/approval")
    @ApiOperation("指导员申请单审批")
    public ResponseResult approval(@ApiParam("指导员id") @RequestParam Long id,
                                   @ApiParam("审核状态 0待审核1审核通过2审核驳回") @RequestParam int auditState,
                                   @ApiParam("审核意见") @RequestParam(required = false) String auditIdea,
                                   HttpServletRequest request){
        instructorApplyService.approval(id,auditState,auditIdea,getCurUserId(request));
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
        List<InstructorApply> applies = instructorApplyService.getAuthInfo(getCurUserId(request),instructorId);
        return ResponseResult.ok(applies);
    }
}