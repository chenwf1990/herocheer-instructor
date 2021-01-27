package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.entity.SysOperationLog;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;
import com.herocheer.instructor.service.SysOperationLogService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaorh
 * @desc 后台用户操作日志表，如：后台登录、指导员入驻(SysOperationLog)表控制层
 * @date 2021-01-25 11:11:06
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/log")
@Api(tags = "操作日志")
public class SysOperationLogController extends BaseController {
    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 根据id删除系统操作日志
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult<SysDept>}
     */
    @PostMapping("/{id:\\w+}")
    @ApiOperation("删除操作日志")
    @AllowAnonymous
    public ResponseResult<SysOperationLog> dropSysOperationLogById(@ApiParam("日志ID") @PathVariable Long id, HttpServletRequest request){
        sysOperationLogService.removeSysOperationLogById(id);
        return ResponseResult.ok();
    }

    /**
     * 系统操作日志列表
     *
     * @param sysOperationLogVO 系统操作日志签证官
     * @param request           请求
     * @return {@link ResponseResult<Page<SysDept>>}
     */
    @PostMapping("/page")
    @ApiOperation("操作日志列表")
    @AllowAnonymous
    public ResponseResult<Page<SysOperationLog>> fecthSysOperationLogByPage(@ApiParam("操作日志") @RequestBody SysOperationLogVO sysOperationLogVO, HttpServletRequest request){
        return ResponseResult.ok(sysOperationLogService.findSysOperationLogByPage(sysOperationLogVO));
    }

}