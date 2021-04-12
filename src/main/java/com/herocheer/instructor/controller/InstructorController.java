package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public ResponseResult<Page<Instructor>> queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<Instructor> page = instructorService.queryPageList(instructorQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询指导员")
    public ResponseResult<Instructor> get(@ApiParam("指导员id") @RequestParam Long id){

        return ResponseResult.ok(instructorService.get(id));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除指导员")
    public ResponseResult delete(@ApiParam("指导员id") @RequestParam Long id){

        return ResponseResult.isSuccess(instructorService.deleteInstructor(id));
    }


    @PostMapping("/instructorImport")
    @ApiOperation("指导员导入")
    public ResponseResult instructorImport(MultipartFile multipartFile,HttpServletRequest request){
        instructorService.instructorImport(multipartFile,request);
        return ResponseResult.ok();
    }

    /**
     * 获取所有指导员
     *
     * @param request 请求
     * @return {@link ResponseResult<Instructor>}
     */
    @GetMapping("/instructores")
    @ApiOperation("所有指导员")
    public ResponseResult<List<Instructor>> fetchInstructorAll(HttpServletRequest request){
        return ResponseResult.ok(instructorService.findByLimit(new HashMap<String,Object>()));
    }
}