package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.vo.RoleAreaVO;
import com.herocheer.instructor.domain.vo.RoleMenuVO;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.instructor.service.SysRoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author gaorh
 * @desc 后台角色表(SysRole)表控制层
 * @date 2021-01-07 17:48:15
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/role")
@Api(tags = "系统角色")
@Slf4j
public class SysRoleController extends BaseController {
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 创建角色
     *
     * @param sysRoleVO VO
     * @param request   请求
     * @return {@link ResponseResult<SysRole>}
     */
    @PostMapping
    @ApiOperation("新增角色")
    public ResponseResult<SysRole> createRole(@ApiParam("系统角色") @Valid @RequestBody SysRoleVO sysRoleVO, HttpServletRequest request){
        return ResponseResult.ok(sysRoleService.addRole(sysRoleVO));
    }


    /**
     * 给角色分配菜单权限
     *
     * @param request    请求
     * @param roleMenuVO VO
     * @return {@link ResponseResult<SysRole>}
     */
    @PostMapping("/menu")
    @ApiOperation("分配菜单权限")
    public ResponseResult<SysRole> settingMenuToRole(@ApiParam("角色ID") @Valid @RequestBody RoleMenuVO roleMenuVO, HttpServletRequest request){
        sysRoleService.settingMenuToRole(roleMenuVO.getMenuId(),roleMenuVO.getRoleId());
        return ResponseResult.ok();
    }

    /**
     * 给角色分配区域权限
     *
     * @param roleAreaVO 作用区域签证官
     * @param request    请求
     * @return {@link ResponseResult<SysRole>}
     */
    @PostMapping("/area")
    @ApiOperation("分配数据权限")
    @AllowAnonymous
    public ResponseResult<SysRole> settingAreaToRole(@ApiParam("角色ID") @RequestBody RoleAreaVO roleAreaVO, HttpServletRequest request){
        sysRoleService.settingAreaToRole(roleAreaVO.getAreaId(),roleAreaVO.getRoleId());
        return ResponseResult.ok();
    }
    /**
     * 根据id删除角色
     *
     * @param request 请求
     * @return {@link ResponseResult<SysRole>}
     */
    @DeleteMapping("/{id:\\w+}")
    @ApiOperation("删除角色")
    @AllowAnonymous
    public ResponseResult dropRoleById(@ApiParam("角色ID") @PathVariable Long id, HttpServletRequest request){
        sysRoleService.removeRoleById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取角色信息
     *
     * @param request 请求
     * @return {@link ResponseResult<SysRole>}
     */
    @GetMapping("/{id:\\w+}")
    @ApiOperation("回显角色")
    @AllowAnonymous
    public ResponseResult<SysRole> fecthRoleById(@ApiParam("角色ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysRoleService.findRoleById(id));
    }

    /**
     * 编辑角色
     *
     * @param request 请求
     * @return {@link ResponseResult<SysRole>}
     */
    @PutMapping
    @ApiOperation("编辑角色")
    public ResponseResult<SysRole> editRole(@ApiParam("系统角色") @Valid @RequestBody SysRoleVO sysRoleVO, HttpServletRequest request){
        return ResponseResult.ok(sysRoleService.modifyRole(sysRoleVO));
    }

    /**
     * 分页查询角色
     *
     * @param sysRoleVO 系统角色签证官
     * @param request   请求
     * @return {@link ResponseResult<Page<SysRole>>}
     */
    @PostMapping("/page")
    @ApiOperation("角色列表")
    public ResponseResult<Page<SysRole>> fecthRoleByPage(@ApiParam("系统角色") @RequestBody SysRoleVO sysRoleVO,HttpServletRequest request){
        return ResponseResult.ok(sysRoleService.findRoleByPage(sysRoleVO));
    }

    /**
     * 获取角色名称
     *
     * @param request 请求
     * @return {@link ResponseResult<SysRole>}
     */
    @GetMapping("/name")
    @ApiOperation("角色名称")
    public ResponseResult<List<SysRole>> fetchRole(@ApiParam("系统标识") @RequestParam(required = false) Integer mark, HttpServletRequest request){
        return ResponseResult.ok( sysRoleService.findRole(mark));
    }

}