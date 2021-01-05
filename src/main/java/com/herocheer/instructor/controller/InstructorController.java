package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenwf
 * @desc  指导员表(Instructor)表控制层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/instructor")
@Api(tags = "指导员")
public class InstructorController extends BaseController{
    @Resource
    private InstructorService instructorService;

    @PostMapping("/queryPageList")
    @ApiOperation("指导员列表查询")
    public ResponseResult queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<Instructor> page = instructorService.queryPageList(instructorQueryVo);
        return ResponseResult.ok().setData(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询指导员")
    public ResponseResult get(@ApiParam("指导员id") @RequestParam Long id){

        return ResponseResult.ok().setData(instructorService.get(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增指导员")
    public ResponseResult add(@RequestBody Instructor instructor){
        instructorService.addInstructor(instructor);
        return ResponseResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑指导员")
    public ResponseResult update(@RequestBody Instructor instructor){
        return ResponseResult.isSuccess(instructorService.update(instructor));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除指导员")
    public ResponseResult delete(@ApiParam("指导员id") @RequestParam Long id){
        return ResponseResult.isSuccess(instructorService.delete(id));
    }

    @GetMapping("/approval")
    @ApiOperation("指导员审批")
    public ResponseResult approval(@ApiParam("指导员id") @RequestParam Long id,
                                   @ApiParam("审核状态 0待审核1审核通过2审核驳回") @RequestParam int auditState,
                                   @ApiParam("审核意见") @RequestParam String auditIdea){
        instructorService.approval(id,auditState,auditIdea);
        return ResponseResult.ok();
    }


    @GetMapping("/getApprovalLog")
    @ApiOperation("指导员审批日志列表")
    public ResponseResult getApprovalLog(@ApiParam("指导员id") @RequestParam Long instructorId){
        List<InstructorLog> logs = instructorService.getApprovalLog(instructorId);
        return ResponseResult.ok().setData(logs);
    }

}