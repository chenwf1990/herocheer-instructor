package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysUserRole;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 微信用户、后台用户、后台管理员(User)表数据库访问层
 * @date 2021-01-18 15:45:21
 * @company 厦门熙重电子科技有限公司
 */
public interface UserDao extends BaseDao<User, Long> {
    /**
     * 返回一个用户信息
     *
     * @param map
     * @return {@link User}
     */
    User selectSysUserOne(Map<String, Object> map);

    /**
     * 分页查询系统用户
     *
     * @param sysUserVO 系统用户签证官
     * @return {@link List <User>}
     */
    List<User> selectSysUserByPage(SysUserVO sysUserVO);

    /**
     * 批量插入系统用户角色
     *
     * @param sysUserRoles 系统用户角色
     * @return int
     */
    int insertBatchSysUserRole(List<SysUserRole> sysUserRoles);

    /**
     * 删除系统用户角色
     *
     * @param userId 用户id
     * @return int
     */
    int deleteSysUserRole(Long userId);
}