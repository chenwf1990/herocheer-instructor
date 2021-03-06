package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.vo.CourseTearcherVO;
import com.herocheer.instructor.domain.vo.TearcherVO;
import com.herocheer.instructor.service.CourseTearcherService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author gaorh
 * @desc 授课老师(CourseTearcher)表控制层
 * @date 2021-03-29 17:12:09
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/tearcher")
@Api(tags = "授课老师")
public class CourseTearcherController extends BaseController {
    @Autowired
    private CourseTearcherService courseTearcherService;

    @Resource
    private InstructorService instructorService;

    /**
     * 创建字典
     *
     * @param courseTearcherVO VO
     * @param request   请求
     * @return {@link ResponseResult < CourseTearcher >}
     */
    @PostMapping
    @ApiOperation("新增老师")
    public ResponseResult<CourseTearcher> createTearcher(@ApiParam("授课老师") @Valid @RequestBody CourseTearcherVO courseTearcherVO, HttpServletRequest request){
        return ResponseResult.ok(courseTearcherService.addCourseTearcher(courseTearcherVO));
    }


    /**
     * 根据id删除字典
     *
     * @param request 请求
     * @return {@link ResponseResult<CourseTearcher>}
     */
    @DeleteMapping("/{id:\\w+}")
    @ApiOperation("删除老师")
    public ResponseResult dropTearcherById(@ApiParam("老师ID") @PathVariable Long id, HttpServletRequest request){
        courseTearcherService.delete(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取字典信息
     *
     * @param request 请求
     * @return {@link ResponseResult<CourseTearcher>}
     */
    @GetMapping("/name/{id:\\w+}")
    @ApiOperation("老师详情")
    public ResponseResult<CourseTearcher> fecthTearcherById(@ApiParam("老师ID") @PathVariable Long id, HttpServletRequest request){
        CourseTearcher courseTearcher = courseTearcherService.get(id);
        // 指导员头像
        if(courseTearcher != null){
            Instructor instructor =instructorService.findByPhone(courseTearcher.getPhone());
            if (ObjectUtils.isEmpty(instructor)) {
                courseTearcher.setHeadPic("");
            } else {
                courseTearcher.setHeadPic(instructor.getHeadPic());
            }
        }
        return ResponseResult.ok(courseTearcher);
    }

    /**
     * 编辑字典
     *
     * @param request 请求
     * @return {@link ResponseResult<CourseTearcher>}
     */
    @PutMapping
    @ApiOperation("编辑老师")
    public ResponseResult<CourseTearcher> editTearcher(@ApiParam("编辑老师") @Valid @RequestBody CourseTearcherVO courseTearcherVO, HttpServletRequest request){
        return ResponseResult.ok(courseTearcherService.modifyCourseTearcher(courseTearcherVO));
    }

    /**
     * 分页查询字典
     *
     * @param courseTearcherVO 系统字典签证官
     * @param request   请求
     * @return {@link ResponseResult<  Page  <CourseTearcher>>}
     */
    @PostMapping("/page")
    @ApiOperation("老师列表")
    public ResponseResult<Page<CourseTearcher>> fecthTearcherByPage(@ApiParam("授课老师") @RequestBody CourseTearcherVO courseTearcherVO,HttpServletRequest request){
        return ResponseResult.ok(courseTearcherService.findCourseTearcherByPage(courseTearcherVO));
    }

    /**
     * 获取所有老师信息
     *
     * @param request 请求
     * @return {@link ResponseResult<List<TearcherVO>>}
     */
    @GetMapping("/all")
    @ApiOperation("老师姓名")
    public ResponseResult<List<TearcherVO>> fetchTearcherByName(HttpServletRequest request){
        return ResponseResult.ok(courseTearcherService.findCourseTearcher());
    }

    /**
     * 回填老师信息
     *
     * @param param   参数
     * @param request 请求
     * @return {@link ResponseResult<List<TearcherVO>>}
     */
    @GetMapping("/backfill")
    @ApiOperation("回填信息")
    public ResponseResult<Instructor> fetchTearcherByParam(@ApiParam("参数") @RequestParam String param, HttpServletRequest request){
        return ResponseResult.ok(instructorService.findByCardNo(param));
    }
}