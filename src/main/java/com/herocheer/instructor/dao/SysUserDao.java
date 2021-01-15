package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysUser;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 后台用户表（管理员、用户）
 * (SysUser)表数据库访问层
 * @date 2021-01-07 17:49:06
 * @company 厦门熙重电子科技有限公司
 */
public interface SysUserDao extends BaseDao<SysUser, Long> {

    /**
     * 返回一个用户信息
     *
     * @param map
     * @return {@link SysUser}
     */
    SysUser selectSysUserOne(Map<String, Object> map);

    /**
     * 分页查询系统用户
     *
     * @param sysUserVO 系统用户签证官
     * @return {@link List<SysUser>}
     */
    List<SysUser> selectSysUserByPage(SysUserVO sysUserVO);
}