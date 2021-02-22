package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.*;

import java.util.List;

/**
 * @author gaorh
 * @desc 微信用户、后台用户、后台管理员(User)表服务接口
 * @date 2021-01-18 15:45:22
 * @company 厦门熙重电子科技有限公司
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     * @return {@link String}
     */
    String login(String account,String password);

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
     * @return {@link User}
     */
    User addUser(SysUserVO sysUserVO);

    /**
     * 寻找用户信息列表
     *
     * @param sysUserVO 用户签证官
     * @return {@link Page <User>}
     */
    Page<User> findUserByPage(SysUserVO sysUserVO);

    /**
     * 根据userId查找用户信息
     *
     * @param id id
     * @return {@link SysUserVO}
     */
    SysUserVO findUserById(Long id);
    /**
     * 修改用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link User}
     */
    User modifyUser(SysUserVO sysUserVO);

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId      用户id
     */
    void modifyPassword(Long userId,String oldPassword,String newPassword);

    /**
     * 重置密码
     *
     * @param userId 用户id
     */
    ResponseResult resetPassword(Long userId);

    /**
     * 根据openId获取微信用户信息
     *
     * @param openId id
     * @return {@link User}
     */
    User findUserByOpenId(String openId);


    /**
     * 根据电话获取微信用户信息
     *
     * @param phone 电话
     * @return {@link User}
     */
    User findUserByPhone(String phone);

    /**
     * 添加微信用户
     *
     * @param weChatUserVO VO
     * @return {@link User}
     */
    User addWeChatUser(WeChatUserVO weChatUserVO);

    /**
     * 修改微信用户
     *
     * @param weChatUserVO VO
     * @return {@link User}
     */
    User modifyWeChatUser(WeChatUserVO weChatUserVO);

    /**
     * 查询用户信息
     *
     * @return {@link List<SysUserVO>}
     */
    List<MemberVO> findUser();

    /**
     * 根据userType查询用户
     *
     * @param userType 用户类型
     * @return {@link List<MemberVO>}
     */
    List<MemberVO> findUserByUserType(String userType);

    /**
     * 添加用户信息
     * @param name
     * @param cardNo
     * @param sex
     * @param phone
     * @param userType
     * @param areaName
     * @param workUnit
     * @return
     */
    User addUser(String name, String cardNo, Integer sex, String phone, Integer userType, String areaName, String workUnit);

    /**
     * 根据用户姓名查找用户信息
     * @param userNames
     * @return
     */
    List<User> findUserByUserNames(List<String> userNames);

    /**
     * 根据用户姓名查找用户信息（指导项目）
     * @param userNames
     * @return
     */
    List<UserGuideProjectVo> findUserProjectByUserNames(List<String> userNames);

    /**
     * 根据用户id查找用户信息（指导项目）
     * @param userIdList
     * @return
     */
    List<UserGuideProjectVo> findUserProjectByUserIds(List<Long> userIdList);

    /**
     * 通过当前用户找到区域
     *
     * @param userId 用户id
     * @return {@link AreaPermissionVO}
     */
    AreaPermissionVO findAreaByCurrentUser(Long userId);

    /**
     * 当前用户的菜单
     *
     * @param id id
     * @return {@link String}
     */
    String findMenuByCurrentUser(Long id);

    /**
     * 当前用户的角色
     *
     * @param user 用户
     */
    void findRoleByCurrentUser(User user);

    void updateUser(User user);
    /**
     * 找到用户的角色权限
     *
     * @param id id
     * @return {@link List<String>}
     */
    List<String> findRoleId(Long id);

    /**
     * 删除系统用户
     *
     * @param id id
     * @return int
     */
    int removeSysUserById(Long id);

    /**
     * 查找用户信息
     * @param curUserId
     * @return
     */
    UserInfoVo findUserInfo(Long curUserId);
}