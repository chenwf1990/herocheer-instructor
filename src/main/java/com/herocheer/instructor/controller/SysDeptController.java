
package com.herocheer.instructor.controller;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysDeptVO;
import com.herocheer.instructor.service.SysDeptService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gaorh
 * @desc 机构表、部门表、部门架构表(SysDept)表控制层
 * @date 2021-01-07 17:44:30
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "组织机构")
@Slf4j
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 创建机构
     *
     * @param sysDeptVO VO
     * @param request   请求
     * @return {@link ResponseResult < SysDept >}
     */
    @PostMapping
    @ApiOperation("新增机构")
    public ResponseResult<SysDept> createDept(@ApiParam("系统机构") @RequestBody SysDeptVO sysDeptVO, HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.addDept(sysDeptVO));
    }

    /**
     * 根据id删除机构
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDept>}
     */
    @PostMapping("/{id:\\w+}")
    @ApiOperation("删除机构")
    public ResponseResult<SysDept> dropDeptById(@ApiParam("机构ID") @PathVariable Long id, HttpServletRequest request){
        sysDeptService.removeDeptById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取机构信息
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDept>}
     */
    @GetMapping("/{id:\\w+}")
    @ApiOperation("回显机构")
    public ResponseResult<SysDept> fecthDeptById(@ApiParam("机构ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.findDeptById(id));
    }

    /**
     * 编辑机构
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDept>}
     */
    @PutMapping
    @ApiOperation("编辑机构")
    public ResponseResult<SysDept> editDept(@ApiParam("系统机构") @RequestBody SysDeptVO sysDeptVO, HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.modifyDept(sysDeptVO));
    }

    /**
     * 分页查询机构
     *
     * @param sysDeptVO 系统机构签证官
     * @param request   请求
     * @return {@link ResponseResult< Page <SysDept>>}
     */
    @PostMapping("/page")
    @ApiOperation("机构列表")
    public ResponseResult<Page<SysDept>> fecthDeptByPage(@ApiParam("系统机构") @RequestBody SysDeptVO sysDeptVO,HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.findDeptByPage(sysDeptVO));
    }

    /**
     * 获取机构名称
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDept>}
     */
    @GetMapping("/name")
    @ApiOperation("机构名称")
    public ResponseResult<List<SysDept>> fetchDept(HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.findDept());
    }

    /**
     * 获取部门树
     *
     * @param request 请求
     * @return {@link ResponseResult<OptionTreeVO>}
     */
    @GetMapping("/tree")
    @ApiOperation("组织机构树")
    public ResponseResult<List<Tree<Long>>> fetchDeptTree(HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.findDeptTree());
    }


    /**
     * 根据id获取子机构
     *
     * @param request   请求
     * @param sysDeptVO 系统部门签证官
     * @return {@link ResponseResult<Page<SysArea>>}
     */
    @PostMapping("/tree/page")
    @ApiOperation("根据id获取子机构")
    public ResponseResult<Page<SysDept>> fetchDeptById(@ApiParam("系统机构") @RequestBody SysDeptVO sysDeptVO, HttpServletRequest request){
        return ResponseResult.ok(sysDeptService.findDeptById(sysDeptVO));
    }

}