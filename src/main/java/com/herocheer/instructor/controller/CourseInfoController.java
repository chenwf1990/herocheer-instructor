package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourseApproval;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.vo.CourseInfoQueryVo;
import com.herocheer.instructor.domain.vo.CourseInfoVo;
import com.herocheer.instructor.enums.CourseApprovalState;
import com.herocheer.instructor.service.CourseInfoService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author makejava
 * @desc  课程信息主表(CourseInfo)表控制层
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/course")
@Api(tags = "课程管理")
public class CourseInfoController extends BaseController{
    @Resource
    private CourseInfoService courseInfoService;

    @PostMapping("/queryPage")
    @ApiOperation("课程信息列表查询")
    public ResponseResult<Page<CourseInfoVo>> queryPageList(@RequestBody CourseInfoQueryVo queryVo, HttpServletRequest request){
        Page<CourseInfoVo> page = courseInfoService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }

    @GetMapping("/withdraw")
    @ApiOperation("课程撤回")
    public ResponseResult withdraw(@ApiParam("课程id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.withdraw(id));
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询课程详情")
    public ResponseResult<CourseInfo> get(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.get(id));
    }

    @GetMapping("/approval/record")
    @ApiOperation("根据id查询课程审批记录")
    public ResponseResult<List<CourseApproval>> approvalRecord(@ApiParam("课程信息id") @RequestParam Long id){
        return ResponseResult.ok(courseInfoService.approvalRecord(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增课程信息")
    public ResponseResult add(@RequestBody CourseInfo courseInfo){
        courseInfo.setState(CourseApprovalState.PENDING.getState());
        Integer count=courseInfoService.insert(courseInfo);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("更新课程信息")
    public ResponseResult update(@RequestBody CourseInfo courseInfo){
        courseInfo.setState(CourseApprovalState.PENDING.getState());
        Integer count=courseInfoService.update(courseInfo);
        return ResponseResult.isSuccess(count);
    }
    @PostMapping("/approval")
    @ApiOperation("课程审批")
    public ResponseResult approval(@RequestBody CourseApproval courseApproval){
        Integer count=courseInfoService.approval(courseApproval);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除课程信息")
    public ResponseResult delete(@ApiParam("招募信息id") @RequestParam Long id){
        return ResponseResult.isSuccess(courseInfoService.delete(id));
    }

}