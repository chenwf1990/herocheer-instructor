package com.herocheer.instructor.dao;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.instructor.domain.entity.SysUserRole;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.MemberVO;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.UserGuideProjectVo;
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

    /**
     * 根据用户姓名查找用户信息
     *
     * @param userNames
     * @return int
     */
    List<User> findUserByUserNames(List<String> userNames);

    /**
     * 根据用户姓名查找用户信息（指导项目）
     * @param userNames
     * @return
     */
    List<UserGuideProjectVo> findUserProjectByUserNames(List<String> userNames);

    /**
     * 根据用户Id查找用户信息（指导项目）
     * @param userIds
     * @return
     */
    List<UserGuideProjectVo> findUserProjectByUserIds(List<Long> userIds);

    /**
     * 当前用户的角色
     *
     * @param id id
     * @return {@link List<String>}
     */
    List<String> selectedRole(Long id);

    /**
     * 当前用户的菜单
     *
     * @param id id
     * @return {@link List<JSONObject>}
     */
    List<JSONObject> selectedMenu(Long id);

    /**
     * 当前用户的区域
     *
     * @param id id
     * @return {@link List<JSONObject>}
     */
    List<JSONObject> selectedArea(Long id);

    /**
     * 删除系统用户
     *
     * @param map 地图
     * @return int
     */
    int delectSysUserById(Map<String, Object> map);

    /**
     * 通过用户id找到角色ID
     *
     * @param userId 用户id
     * @return {@link List<Long>}
     */
    List<String> findRoleByUserId(Long userId);

    /**
     * 根据userType查询用户
     *
     * @param userType 用户类型
     * @return {@link List<User>}
     */
    List<MemberVO> findUserByUserType(List<String> userType);

    /**
     * 重置openid为null
     *
     * @param openid openid
     */
    void resetByOpenId(String openid);
}