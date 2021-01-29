package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysOperation;
import com.herocheer.instructor.domain.vo.SysOperationVO;
import com.herocheer.instructor.service.SysOperationService;
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

/**
 * @author gaorh
 * @desc 功能操作表(SysOperation)表控制层
 * @date 2021-01-07 17:46:31
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/operation")
@Api(tags = "操作功能")
@Slf4j
public class SysOperationController extends BaseController {
    @Resource
    private SysOperationService sysOperationService;


    /**
     * 创建操作
     *
     * @param sysOperationVO VO
     * @param request        请求
     * @return {@link ResponseResult<SysOperation>}
     */
    @PostMapping
    @ApiOperation("新增操作")
    @AllowAnonymous
    public ResponseResult<SysOperation> createOperation(@ApiParam("系统操作") @Valid @RequestBody SysOperationVO sysOperationVO, HttpServletRequest request){
        return ResponseResult.ok(sysOperationService.addOperation(sysOperationVO));
    }


    /**
     * 根据id删除操作
     *
     * @param request 请求
     * @return {@link ResponseResult<SysOperation>}
     */
    @DeleteMapping("/{id:\\w+}")
    @ApiOperation("删除操作")
    @AllowAnonymous
    public ResponseResult dropOperationById(@ApiParam("操作ID") @PathVariable Long id, HttpServletRequest request){
        sysOperationService.removeOperationById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取操作信息
     *
     * @param request 请求
     * @return {@link ResponseResult<SysOperation>}
     */
    @GetMapping("/{id:\\w+}")
    @ApiOperation("回显操作")
    @AllowAnonymous
    public ResponseResult<SysOperation> fecthOperationById(@ApiParam("操作ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysOperationService.findOperationById(id));
    }

    /**
     * 编辑操作
     *
     * @param request 请求
     * @return {@link ResponseResult<SysOperation>}
     */
    @PutMapping
    @ApiOperation("编辑操作")
    @AllowAnonymous
    public ResponseResult<SysOperation> editOperation(@ApiParam("系统操作") @Valid @RequestBody SysOperationVO sysOperationVO, HttpServletRequest request){
        return ResponseResult.ok(sysOperationService.modifyOperation(sysOperationVO));
    }

}