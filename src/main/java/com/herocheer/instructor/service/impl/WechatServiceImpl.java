package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.enums.WechatConst;
import com.herocheer.instructor.service.WechatService;
import com.herocheer.instructor.utils.IDSAPIClient;
import com.herocheer.instructor.utils.IDSAPIEncryptUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import com.trs.idm.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
public class WechatServiceImpl extends BaseServiceImpl<UserDao, User, Long> implements WechatService {

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${ixm.secret}")
    private String ixmSecret;
    @Value("${ixm.redirectUrl}")
    private String redirectUrl;
    @Value("${ixm.userUrl}")
    private String ixmUserUrl;
    @Value("${ixm.appName}")
    private String appName;

    @Resource
    private RedisClient redisClient;

    @Autowired
    private RestTemplate restTemplate;

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
        param.put("appid", appid);
        param.put("secret", secret);
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
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
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
        wxInfoVO.setAppId(appid);
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
        String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid
                + "&secret="+secret
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


    @Override
    public User ixmUserIsLogin(HttpSession session, String code, String openid) {
        if (StringUtils.isBlank(openid) && StringUtils.isBlank(code)) {
            throw new CommonException("错误入参");
        }
        if (StringUtils.isBlank(openid) && StringUtils.isNotBlank(code)) {
            openid = getOpenId(code);
            if (StringUtils.isBlank(openid)) {
                throw new CommonException("OPENID获取失败");
            }
        }

        Map map = new HashMap();
        map.put("openid", openid);
        User user  = this.dao.selectSysUserOne(map);

        if (user == null) {
            user = User.builder().build();
            user.setOpenid(openid);
            return user;
        }

        // user.getIxmLoginStatus() == 0 登出
        if (user.getIxmLoginStatus()) {
            user = User.builder().build();
            user.setOpenid(openid);
            return user;
        }
        user.setIxmToken("");
        session.setAttribute(WechatConst.SESSION_USER, user);
        return user;
    }

    @Override
    public String ixmLoginUrl(String callBackUrl) {
        try {
            String encryptedData = Base64Util.encode(IDSAPIEncryptUtil.encrypt(callBackUrl, MD5, ixmSecret));
            //加密时间戳参数 ，时间戳精确到秒
            String createTime = Base64Util.encode(IDSAPIEncryptUtil.encrypt(String.valueOf(System.currentTimeMillis() / 1000), MD5, ixmSecret));
            String ixmLoginUrl = String.format(redirectUrl, encryptedData, createTime);
            log.info("ixmLoginUrl:{}", ixmLoginUrl);
            return ixmLoginUrl;
        } catch (Exception ex) {
            log.info("ixmLoginUrl Exception", ex);
            throw new CommonException("i厦门登录跳转异常");
        }
    }

    @Override
    public User ixmLogin(HttpServletRequest request, HttpSession session, String openid, String token) {
        String result = "";
        String ssoSessionId = "";
        try {
            log.info("wx openid:{}", openid);
            log.info("ixm token:{}", token);
            ssoSessionId = token.split("_")[0];
            String data = "clientIP=" + request.getRemoteAddr() + "&coSessionId="
                    + session.getId() + "&ssoSessionId=" + ssoSessionId;

            IDSAPIClient client = new IDSAPIClient(ixmUserUrl, ixmSecret, "MD5", "json");
            //登录认证，返回登录信息
            result = client.processor(appName, data);
            log.info("ixm getUserResult:{}", result);
            if (StringUtils.isBlank(result)) {
                throw new CommonException("获取用户信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
        if (jsonObject.getInteger("code") != 200) {
            throw new CommonException("获取用户信息异常");
        }
        JSONObject user = JSONObject.parseObject(JSONObject.parseObject
                (jsonObject.getString("data"), JSONObject.class).getString("user"), JSONObject.class);


        //openid被使用置空
        this.dao.resetByOpenId(openid);

        //根据证件号判断用户本地数据是否存在
        String certificateNum = user.getString("certificateNum");
        Map map = new HashMap();
        map.put("certificateNo",certificateNum);
        User sysUser  = this.dao.selectSysUserOne(map);


        if (sysUser == null) {
            sysUser = User.builder().build();
            sysUser.setIxmUserId(user.getString("uuid").replace("-", ""));
            sysUser.setIxmUserName(user.getString("userName"));
            sysUser.setUserName(user.getString("certificateName"));
            if (certificateNum.length() == 18) {//判断证件类型
                if (Integer.parseInt(certificateNum.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sysUser.setSex(2);
                } else {
                    sysUser.setSex(1);
                }
            } else {
                sysUser.setSex(0);
            }
            sysUser.setCertificateNo(certificateNum);
            sysUser.setIxmRealNameLevel(user.getString("realNameStatus"));
            sysUser.setIxmUserRealName(user.getString("certificateName"));
            sysUser.setPhone(user.getString("mobile"));
            sysUser.setInsuranceStatus(0);
            sysUser.setCommitmentStatus(false);
            sysUser.setSource("1");
            sysUser.setIxmLoginStatus(true);
            sysUser.setIxmToken(ssoSessionId);
            sysUser.setOpenid(openid);
            sysUser.setUserType(UserTypeEnums.weChatUser.getCode());

            this.insert(sysUser);

        } else {
            User oldUser = User.builder().build();
            oldUser.setId(sysUser.getId());
            oldUser.setCertificateNo(certificateNum);
            oldUser.setUserName(user.getString("certificateName"));
            oldUser.setPhone(user.getString("mobile"));
            oldUser.setOpenid(openid);
            oldUser.setIxmLoginStatus(true);
            oldUser.setIxmToken(ssoSessionId);
            if (certificateNum.length() == 18) {//判断证件类型
                if (Integer.parseInt(certificateNum.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sysUser.setSex(2);
                    oldUser.setSex(2);
                } else {
                    sysUser.setSex(1);
                    oldUser.setSex(1);
                }
            } else {
                sysUser.setSex(0);
                oldUser.setSex(0);
            }
            oldUser.setUpdateTime(System.currentTimeMillis());

            this.update(oldUser);

            sysUser.setCertificateNo(certificateNum);
            sysUser.setUserName(user.getString("certificateName"));
            sysUser.setPhone(user.getString("mobile"));
            sysUser.setOpenid(openid);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUpdateTime(oldUser.getUpdateTime());
        }
        sysUser.setIxmToken("");
        //用户信息存入session
        session.setAttribute(WechatConst.SESSION_USER, sysUser);
        WeChatUserVO weChatUser = WeChatUserVO.builder().build();
        BeanCopier.create(sysUser.getClass(),weChatUser.getClass(),false).copy(sysUser.getClass(),weChatUser.getClass(),null);
        weChatUser.setToken(IdUtil.simpleUUID());
        return weChatUser;
    }

    public String getOpenId(String code) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(String.format(accessTokenUrl, appid, secret, code),
                    HttpMethod.GET, null, String.class);
            String result = responseEntity.getBody();
            log.info("GetOpenId Result:{}", result);
            JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
            if (jsonObject.containsKey("errcode")) {
                throw new Exception(jsonObject.getString("errmsg"));
            }
            return jsonObject.getString("openid");
        } catch (Exception ex) {
            log.error("GetOpenId Exception : {}", ex);
            return null;
        }
    }
}
