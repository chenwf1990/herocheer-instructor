
package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.entity.SysRoleArea;
import com.herocheer.instructor.domain.entity.SysRoleMenu;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 批量插入角色菜单中间表
     *
     * @param sysRoleMenus 系统角色的菜单
     * @return int
     */
    int insertBatchSysRoleMenu(List<SysRoleMenu> sysRoleMenus);

    /**
     * 批量插入角色区域中间表
     *
     * @param sysRoleAres VO
     * @return int
     */
    int insertBatchSysRoleArea(List<SysRoleArea> sysRoleAres);

    /**
     * 判断角色名是否存在
     *
     * @param map 地图
     * @return {@link SysRole}
     */
    int selectSysRoleOne(Map<String, Object> map);

    /**
     * 检测是否有审批的权限
     * @param curUserId
     * @param roleCode
     * @return
     */
    int checkIsAuditAuth(@Param("userId") Long curUserId, @Param("roleCode")String roleCode);
}