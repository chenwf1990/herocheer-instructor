package com.herocheer.instructor.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appScret}")
    private String appScret;
    @Resource
    private RedisClient redisClient;

    /**
     * @param code
     * @return
     * @author chenwf
     * @desc 根据微信授权码获取accessToken
     * @date 2021/1/26
     */
    @Override
    public JSONObject getAccessTokenByCode(String code) {
        Map<String, Object> param = new HashMap<>();
        param = new HashMap<>();
        param.put("appid", appId);
        param.put("secret", appScret);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String access_url = "https://api.weixin.qq.com/sns/jscode2session";
        String result = HttpUtil.get(access_url, param);
        log.info("获取微信access_token:code={},result={}", code, result);
        JSONObject resultJson = JSONObject.parseObject(result);
        validResult(resultJson);
        return resultJson;
    }

    /**
     * @return
     * @author chenwf
     * @desc 获取redis缓存的accessToken
     * @date 2021/1/26
     */
    @Override
    public String getRedisAccessToken() {
        String key = "wechar_accessToken";
        String accessToken = redisClient.get(key);
        if(StringUtils.isEmpty(accessToken)){
            JSONObject json = getAccessToken();
            accessToken = json.getString("access_token");
            redisClient.set(key,accessToken,6000);
        }
        return accessToken;
    }

    /**
     * @return
     * @author chenwf
     * @desc 获取微信jsTicket
     * @date 2021/1/26
     */
    @Override
    public JSONObject getWechatJsTicket() {
        String access_token = getRedisAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        String result = HttpUtil.get(url);
        JSONObject resultJson = JSONObject.parseObject(result);
        validResult(resultJson);
        return resultJson;
    }

    @Override
    public String getRedisWechatJsTicket() {
        String key = "wechat_jsapi_ticket";
        String ticket = redisClient.get(key);
        if(StringUtils.isEmpty(ticket)){
            JSONObject jsonObject = getWechatJsTicket();
            ticket = jsonObject.getString("ticket");
            redisClient.set(key,ticket,6000);
        }
        System.out.println("ticket:"+ticket);
        return ticket;
    }

    //{"access_token":"ACCESS_TOKEN","expires_in":7200}
    private JSONObject getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appScret;
        String result = HttpUtil.get(url);
        JSONObject resultJson = JSONObject.parseObject(result);
        validResult(resultJson);
        return resultJson;
    }

    //验证微信交互是否正常
    private void validResult(JSONObject resultJson) {
        if (resultJson.containsKey("errcode") && !resultJson.getString("errcode").equals("0")) {
            throw new CommonException("系统错误:"+resultJson.getString("errmsg"));
        }
    }

    /**
     * @return
     * @author chenwf
     * @desc 获取微信信息
     * @date 2021/1/26
     * @param pageUrl
     */
    @Override
    public WxInfoVO getWxInfo(String pageUrl) {

        //1、获取Ticket
        String jsapi_ticket = getRedisWechatJsTicket();

        //2、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        //3、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+pageUrl;

        //4、将字符串进行sha1加密
        String signature = SecureUtil.sha1(str);

        WxInfoVO wxInfoVO = new WxInfoVO();
        wxInfoVO.setAppId(appId);
        wxInfoVO.setNonceStr(noncestr);
        wxInfoVO.setTimestamp(timestamp);
        wxInfoVO.setSignature(signature);
        wxInfoVO.setTicket(jsapi_ticket);
        return wxInfoVO;
    }

    /**
     * @param wecharCode
     * @return
     */
    @Override
    public JSONObject getOauth2(String wecharCode) {
        String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId
                + "&secret="+appScret
                + "&code="+wecharCode
                + "&grant_type=authorization_code";
        String result = HttpUtil.get(codeUrl);
        JSONObject json = JSONObject.parseObject(result);
        validResult(json);
        String accessToken = json.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken
                + "&openid="+json.getString("openid")
                + "&lang=zh_CN";
        String userResult = HttpUtil.get(infoUrl);
        JSONObject userJson = JSONObject.parseObject(userResult);
        validResult(json);
        return userJson;
    }
}
