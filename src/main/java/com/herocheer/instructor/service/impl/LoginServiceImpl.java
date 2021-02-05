package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WechaLoginVo;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private WechatService wechatService;
    @Resource
    private UserService userService;
    @Resource
    private RedisClient redisClient;
    /**
     * @param wechaLoginVo
     * @author chenwf
     * @desc 微信登录
     * @date 2021/1/26
     */
    @Override
    public UserInfoVo wechatLogin(WechaLoginVo wechaLoginVo) {
        UserInfoVo userVO = new UserInfoVo();
        JSONObject jsonStr = wechatService.getOauth2(wechaLoginVo.getCode());
        String openId = jsonStr.getString("openid");
        //查找是否存在该openId用户
        User user = userService.findUserByOpenId(openId);
        if(user == null){

            //1：i健身查找是否存在改用户
            //2：不存在就告诉前端跳转到i运动注册用户信息
            //3：存在就直接创建用户数据
            user = new User();
            user.setUserType(UserTypeEnums.weChatUser.getCode());
            user.setUserName(openId);
            user.setOpenid(openId);
            userService.insert(user);
        }
        String token = IdUtil.simpleUUID();
        BeanUtils.copyProperties(user,userVO);
        userVO.setToken(token);
        redisClient.set(token,JSONObject.toJSONString(userVO),1 * 24 * 60 * 60);
        return userVO;
    }

    @Override
    public void loginTest(String token, Long userId) {
        JSONObject json = new JSONObject();
        if(userId != null){
            User user = userService.get(userId);
            json = JSONObject.parseObject(JSONObject.toJSONString(user));
        }else{
            json.put("id",1);
            json.put("userName","chenweifeng");
            json.put("userType",1);
            json.put("phone","13655080001");
        }
        redisClient.set(token,json.toJSONString());
    }

}
