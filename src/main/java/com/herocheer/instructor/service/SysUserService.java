package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysUser;
import com.herocheer.instructor.domain.vo.SysUserVO;

/**
 * @author gaorh
 * @desc 后台用户表（管理员、用户）
 * (SysUser)表服务接口
 * @date 2021-01-07 17:49:06
 * @company 厦门熙重电子科技有限公司
 */
public interface SysUserService extends BaseService<SysUser, Long> {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param verCode  版本的代码
     * @return {@link String}
     */
    String login(String username,String password,String verCode);

    /**
     * token:用户相关信息放入redis
     *
     * @param token 令牌
     */
    void hsetUserInfoToRedis(String token);

    /**
     * 添加用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link SysUser}
     */
    SysUser addUser(SysUserVO sysUserVO);

    /**
     * 寻找用户信息列表
     *
     * @param sysUserVO 用户签证官
     * @return {@link Page<SysUser>}
     */
    Page<SysUser> findUserByPage(SysUserVO sysUserVO);
    /**
     * 修改用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link SysUser}
     */
    SysUser modifyUser(SysUserVO sysUserVO);

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId      用户id
     */
    void modifyPassword(Long userId,String oldPassword,String newPassword);
}