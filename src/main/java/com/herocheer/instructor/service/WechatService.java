package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.domain.vo.WxInfoVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
public interface WechatService {
    /**
     * @author chenwf
     * @desc 获取微信信息
     * @date 2021/1/26
     * @return
     * @param pageUrl
     */
    WxInfoVO getWxInfo(String pageUrl);

    UserInfoVo ixmUserIsLogin(HttpSession session, String code,String openid,Integer mark);

    String ixmLoginUrl(String callBackUrl);

    User ixmLogin(HttpServletRequest request, HttpSession session, String openid, String token,UserInfoVo currentUse,Integer mark);

    /**
     * 绑定用户
     *
     * @param correntUser 当前用户
     * @param phone       电话
     * @return {@link UserInfoVo}
     */
    UserInfoVo bindingWeChat(UserEntity correntUser, String phone);

    /**
     * 添加用户信息
     *
     * @param weChatUser 用户
     * @return int
     */
    int addUserInfo(WeChatUserVO weChatUser);

    /**
     * 微信列表
     *
     * @param sysUserVO 系统用户签证官
     * @return {@link Page<User>}
     */
    Page<User> findWeChatUserByPage(SysUserVO sysUserVO);

    /**
     * 发送微信消息
     *
     * @param userList         订阅课程的用户名单
     * @param courseInfo       课程信息
     * @param courseScheduleId 课程安排id
     */
    void sendWechatMessages(Collection<String> userList, CourseInfo courseInfo, Long courseScheduleId );

    /**
     * ixmAPP应用登录网址
     *
     * @param redirectUri 重定向的uri
     * @return {@link String}
     */
    String ixmAppLoginUrl(String redirectUri);

    /**
     * ixmAPP应用登录
     *
     * @param request     请求
     * @param session     会话
     * @param code        代码
     * @param redirectUri 重定向的uri
     * @return {@link UserInfoVo}
     */
    UserInfoVo ixmAppLogin(HttpServletRequest request, HttpSession session, String code, String redirectUri);

    /**
     * 烟登录
     *
     * @param session 会话
     * @param token   令牌
     * @return {@link UserInfoVo}
     */
    UserInfoVo smkLogin(HttpSession session, String token);
}
