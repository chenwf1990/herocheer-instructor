package com.herocheer.instructor.controller;

import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorCert;
import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private RedisClient redisClient;

    @PostMapping("/queryPageList")
    @ApiOperation("指导员列表查询")
    @AllowAnonymous
    public ResponseResult<Page<Instructor>> queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<Instructor> page = instructorService.queryPageList(instructorQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询指导员")
    @AllowAnonymous
    public ResponseResult<Instructor> get(@ApiParam("指导员id") @RequestParam Long id){

        return ResponseResult.ok(instructorService.get(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增指导员")
    public ResponseResult add(@RequestBody Instructor instructor, HttpServletRequest request){
        instructorService.addInstructor(instructor,getCurUserId(request));
        return ResponseResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("编辑指导员")
    public ResponseResult update(@RequestBody Instructor instructor){
        return ResponseResult.isSuccess(instructorService.updateInstructor(instructor));
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
                                   @ApiParam("审核意见") @RequestParam(required = false) String auditIdea){
        instructorService.approval(id,auditState,auditIdea);
        return ResponseResult.ok();
    }


    @GetMapping("/getApprovalLog")
    @ApiOperation("指导员审批日志列表")
    public ResponseResult<List<InstructorLog>> getApprovalLog(@ApiParam("指导员id") @RequestParam Long instructorId){
        List<InstructorLog> logs = instructorService.getApprovalLog(instructorId);
        return ResponseResult.ok(logs);
    }

    @GetMapping("/getInstructorCertList")
    @ApiOperation("指导员证书列表")
    public ResponseResult<List<InstructorCert>> getInstructorCertList(@ApiParam("指导员id") @RequestParam Long instructorId){
        List<InstructorCert> logs = instructorService.getInstructorCertList(instructorId);
        return ResponseResult.ok(logs);
    }


    @PostMapping("/loginTest")
    @ApiOperation("模拟测试登录")
    @AllowAnonymous
    public ResponseResult loginTest(@ApiParam("key值") @RequestParam String token){
        if(StringUtils.isEmpty(token)){
            token = "chenweifeng";
        }
//        JSONObject json = new JSONObject();
//        json.put("id",1);
//        json.put("userName","chenweifeng");
//        json.put("userType",1);
//        json.put("phone","13655080001");
//        redisClient.set(token,json.toJSONString());
        instructorService.loginTest(token);
        return ResponseResult.ok().setMessage(token);
    }


    @GetMapping("/instructorImport")
    @ApiOperation("指导员导入")
    public ResponseResult<List<InstructorCert>> instructorImport(MultipartFile multipartFile,HttpServletRequest request){
        instructorService.instructorImport(multipartFile,request);
        return ResponseResult.ok();
    }

}