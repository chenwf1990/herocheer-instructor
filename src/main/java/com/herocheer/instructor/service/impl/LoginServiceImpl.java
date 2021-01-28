package com.herocheer.instructor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.WechaLoginVo;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.AesCbcUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
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
    public void wechatLogin(WechaLoginVo wechaLoginVo) {
        //获取微信accessToken
        JSONObject jsonStr = wechatService.getAccessTokenByCode(wechaLoginVo.getWecharCode());
        String sessionKey = jsonStr.getString("session_key");
        String openId = jsonStr.getString("openid");
        String retStr = null;
        try {
            retStr = AesCbcUtil.decrypt(wechaLoginVo.getEncryptedData(), sessionKey, wechaLoginVo.getIv(), "UTF-8");
        } catch (Exception e) {
            throw new CommonException("系统异常，解析失败");

        }
        JSONObject retStrJson = JSONObject.parseObject(retStr);
        String unionId = retStrJson.getString("unionId");
        String nickName = retStrJson.getString("nickName");
        String headPic = retStrJson.getString("avatarUrl");
        String gender = retStrJson.getString("gender"); //性别 0：未知、1：男、2：女
        //1:根据openId获取用户信息

        //2：获取不到用户，就根据openId拉取i健身的用户信息，拉取不到，跳转到i厦门
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
