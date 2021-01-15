package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 后台角色表(SysRole)表数据库访问层
 * @date 2021-01-07 17:48:14
 * @company 厦门熙重电子科技有限公司
 */
public interface SysRoleDao extends BaseDao<SysRole, Long> {

    /**
     * 角色列表
     *
     * @param sysRoleVO VO
     * @return {@link List<SysRole>}
     */
    List<SysRole> selectRoleByPage(SysRoleVO sysRoleVO);

}