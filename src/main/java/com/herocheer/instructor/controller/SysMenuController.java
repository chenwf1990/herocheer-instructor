package com.herocheer.instructor.controller;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;
import com.herocheer.instructor.service.SysMenuService;
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
 * @desc 菜单表(SysMenu)表控制层
 * @date 2021-01-07 17:07:30
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/menus")
@Api(tags = "系统菜单")
@Slf4j
public class SysMenuController extends BaseController {
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 创建菜单
     *
     * @param sysMenuVO VO
     * @param request   请求
     * @return {@link ResponseResult<SysMenu>}
     */
    @PostMapping
    @ApiOperation("新增菜单")
    @AllowAnonymous
    public ResponseResult<SysMenu> createMenu(@Valid @ApiParam("系统菜单") @RequestBody SysMenuVO sysMenuVO, HttpServletRequest request){
        return ResponseResult.ok(sysMenuService.addMenu(sysMenuVO));
    }

    /**
     * 根据id删除菜单
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id:\\w+}")
    @ApiOperation("删除菜单")
    @AllowAnonymous
    public ResponseResult dropMenuById(@ApiParam("菜单ID") @PathVariable Long id, HttpServletRequest request){
        sysMenuService.removeMenuById(id);
        return ResponseResult.ok();
    }

    // TODO 批量删除

    // TODO 启用和禁用功能

    /**
     * 根据id获取菜单
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult<SysMenu>}
     */
    @GetMapping("/{id:\\w+}")
    @ApiOperation("根据ID获取菜单")
    @AllowAnonymous
    public ResponseResult<SysMenu> fetchMenuById(@ApiParam("菜单ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysMenuService.findMenuById(id));
    }

    /**
     * 根据id编辑菜单
     *
     * @param sysMenuVO VO
     * @param request   请求
     * @return {@link ResponseResult<SysMenu>}
     */
    @PutMapping
    @ApiOperation("编辑菜单")
    @AllowAnonymous
    public ResponseResult<SysMenu> editMenuById(@Valid @ApiParam("系统菜单") @RequestBody SysMenuVO sysMenuVO,HttpServletRequest request){
        return ResponseResult.ok(sysMenuService.modifyMenuById(sysMenuVO));
    }

    /**
     * 菜单列表（分页）
     *
     * @param sysMenuVO 系统菜单签证官
     * @param request   请求
     * @return {@link ResponseResult<Page<SysMenu>>}
     */
    @PostMapping("/page")
    @ApiOperation("查询菜单列表")
    @AllowAnonymous
    public ResponseResult<Page<SysMenu>> fetchMenuByPage(@RequestBody SysMenuVO sysMenuVO,HttpServletRequest request){
        Page<SysMenu> page = sysMenuService.findMenuByPage(sysMenuVO);
        return ResponseResult.ok(page);
    }


    /**
     * 用户的菜单权限
     *
     * @param request 请求
     * @return {@link ResponseResult<List<Tree<Long>>>}
     */
    @GetMapping("/tree/user")
    @ApiOperation("菜单权限树")
    @AllowAnonymous
    public ResponseResult<List<Tree<Long>>> fetchMenuTreeToUser(HttpServletRequest request){
        // 当前用户信息
        User user =  User.builder().build();

        List<Tree<Long>> treeList = sysMenuService.findMenuTreeToUser(user);
        return ResponseResult.ok(treeList);
    }

    /**
     * 角色分配菜单权限
     *
     * @param request 请求
     * @param id      id
     * @return {@link ResponseResult<OptionTreeVO>}
     */
    @GetMapping("/tree/role/{id:\\w+}")
    @ApiOperation("菜单树")
    @AllowAnonymous
    public ResponseResult<OptionTreeVO> fetchMenuTreeToRole(@ApiParam("角色ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysMenuService.findMenuTreeToRole(id));
    }
}