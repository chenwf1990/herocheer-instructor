package com.herocheer.instructor.service;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.instructor.domain.vo.WxInfoVO;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
public interface WechatService {
    /**
     * @author chenwf
     * @desc 根据微信授权码获取accessToken
     * @date 2021/1/26
     * @param code
     * @return
     */
    JSONObject getAccessTokenByCode(String code);

    /**
     * @author chenwf
     * @desc 获取redis缓存的accessToken
     * @date 2021/1/26
     * @return
     */
    String getRedisAccessToken();

    /**
     * @author chenwf
     * @desc 获取微信jsTicket
     * @date 2021/1/26
     * @return
     */
    JSONObject getWechatJsTicket();

    String getRedisWechatJsTicket();

    /**
     * @author chenwf
     * @desc 获取微信信息
     * @date 2021/1/26
     * @return
     * @param pageUrl
     */
    WxInfoVO getWxInfo(String pageUrl);
}