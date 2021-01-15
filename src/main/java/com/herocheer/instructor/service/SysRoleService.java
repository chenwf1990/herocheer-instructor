package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.vo.SysRoleVO;

import java.util.List;

/**
 * @author gaorh
 * @desc 后台角色表(SysRole)表服务接口
 * @date 2021-01-07 17:48:14
 * @company 厦门熙重电子科技有限公司
 */
public interface SysRoleService extends BaseService<SysRole, Long> {


    /**
     * 添加角色
     *
     * @param sysRoleVO VO
     * @return {@link SysRole}
     */
    SysRole addRole(SysRoleVO sysRoleVO);

    /**
     * 删除角色
     *
     * @param id id
     */
    void removeRoleById(Long id);

    /**
     * 根据id获取角色信息
     *
     * @param id id
     * @return {@link SysRole}
     */
    SysRole findRoleById(Long id);

    /**
     * 修改角色
     *
     * @param sysRoleVO 系统角色签证官
     * @return {@link SysRole}
     */
    SysRole modifyRole(SysRoleVO sysRoleVO);

    /**
     * 角色列表
     *
     * @param sysRoleVO VO
     * @return {@link Page<SysRole>}
     */
    Page<SysRole>  findRoleByPage(SysRoleVO sysRoleVO);

    /**
     * 下拉框角色名
     *
     * @return {@link List<SysRole>}
     */
    List<SysRole>  findRole();

}