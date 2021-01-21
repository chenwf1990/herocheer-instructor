package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysDict;
import com.herocheer.instructor.domain.vo.SysDictVO;
import com.herocheer.instructor.service.SysDictService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表控制层
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "系统字典表")
@Slf4j
public class SysDictController extends BaseController {
    @Resource
    private SysDictService sysDictService;

    /**
     * 创建字典
     *
     * @param sysDictVO VO
     * @param request   请求
     * @return {@link ResponseResult < SysDict >}
     */
    @PostMapping
    @ApiOperation("新增字典")
    @AllowAnonymous
    public ResponseResult<SysDict> createDict(@ApiParam("系统字典") @Valid @RequestBody SysDictVO sysDictVO, HttpServletRequest request){
        return ResponseResult.ok(sysDictService.addDict(sysDictVO));
    }


    /**
     * 根据id删除字典
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDict>}
     */
    @DeleteMapping("/{id:\\w+}")
    @ApiOperation("删除字典")
    @AllowAnonymous
    public ResponseResult dropDictById(@ApiParam("字典ID") @PathVariable Long id, HttpServletRequest request){
        sysDictService.removeDictById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取字典信息
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDict>}
     */
    @GetMapping("/{id:\\w+}")
    @ApiOperation("回显字典")
    @AllowAnonymous
    public ResponseResult<SysDict> fecthDictById(@ApiParam("字典ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysDictService.findDictById(id));
    }

    /**
     * 编辑字典
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDict>}
     */
    @PutMapping
    @ApiOperation("编辑字典")
    @AllowAnonymous
    public ResponseResult<SysDict> editDict(@ApiParam("系统字典") @Valid @RequestBody SysDictVO sysDictVO, HttpServletRequest request){
        return ResponseResult.ok(sysDictService.modifyDict(sysDictVO));
    }

    /**
     * 分页查询字典
     *
     * @param sysDictVO 系统字典签证官
     * @param request   请求
     * @return {@link ResponseResult< Page <SysDict>>}
     */
    @PostMapping("/page")
    @ApiOperation("字典列表")
    @AllowAnonymous
    public ResponseResult<Page<SysDict>> fecthDictByPage(@ApiParam("系统字典") @RequestBody SysDictVO sysDictVO,HttpServletRequest request){
        return ResponseResult.ok(sysDictService.findDictByPage(sysDictVO));
    }

    /**
     * 根据字典类型查询字典（pid）
     *
     * @param request 请求
     * @return {@link ResponseResult<SysDict>}
     */
    @GetMapping("/name/{pid:\\w+}")
    @ApiOperation("字典名称")
    @AllowAnonymous
    public ResponseResult<List<SysDict>> fetchDict(@ApiParam("字典PID") @PathVariable Long pid, HttpServletRequest request){
        return ResponseResult.ok( sysDictService.findDict(pid));
    }
}