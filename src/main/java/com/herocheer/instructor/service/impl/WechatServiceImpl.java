package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.domain.vo.WxInfoVO;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.enums.InsuranceConst;
import com.herocheer.instructor.enums.SourceEnums;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.enums.WechatConst;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.UserService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
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

    @Autowired
    private UserService userService;

    @Autowired
    private InstructorService instructorService;

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
        String result = findJsapiTicket();
        JSONObject json = JSONObject.parseObject(result);
        String jsapi_ticket = json.getString("ticket");
        //2、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

        //3、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+pageUrl;
        log.info("获取微信信息：{}",str);
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
     * 找到 jsapi_ticket
     *
     * @return {@link String}
     */
    private String findJsapiTicket(){
        Long currentTime = System.currentTimeMillis();
        String sign = DigestUtils.md5DigestAsHex((currentTime + InsuranceConst.KEY).getBytes());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sign", sign);
        paramMap.put("timestamp",currentTime);
        String result= HttpUtil.post(InsuranceConst.BASE_URL+"/weChat/getToken", paramMap);

        JSONObject JSONObj = JSONObject.parseObject(result);
        if(JSONObj == null || JSONObj.getInteger("code") != 200){
            throw new CommonException("请求jsapi_ticket失败");
        }
        return JSONObj.getString("result");
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
        log.info("codeUrl:{}",codeUrl);
        JSONObject json = JSONObject.parseObject(result);
        validResult(json);
        JSONObject userJson = getWeChatUserInfo(json);
        validResult(json);
        return userJson;
    }

    public JSONObject getWeChatUserInfo(JSONObject json) {
        String accessToken = json.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken
                + "&openid="+json.getString("openid")
                + "&lang=zh_CN";
        String userResult = HttpUtil.get(infoUrl);
        return JSONObject.parseObject(userResult);
    }


    @Override
    public UserInfoVo ixmUserIsLogin(HttpSession session, String code,String openid) {
        // 获取微信群众信息，方便统计用户及展示个人中心信息显示
        JSONObject jsonStr = new JSONObject();
        if (StringUtils.isNotEmpty(code)) {
            JSONObject JSONObj = getOpenId(code);
            if (ObjectUtils.isEmpty(JSONObj) || StringUtils.isBlank(JSONObj.getString("openid"))) {
                throw new CommonException("openid未获取成功");
            }
            openid = JSONObj.getString("openid");
            jsonStr  = getWeChatUserInfo(JSONObj);
        }
//        String openid = "or6Q-wfzYsLqaHlof8Tglyvdf-Y8";

        // 根据openid获取用户信息
        Map map = new HashMap();
        map.put("openid", openid);
        User user  = this.dao.selectSysUserOne(map);

        UserInfoVo userInfo = new UserInfoVo();
        if (user == null) {
            user = User.builder().build();
            user.setUserType(UserTypeEnums.weChatUser.getCode());
            // 微信用户群众信息
            user.setNickName(jsonStr.getString("nickname"));
            user.setImgUrl(jsonStr.getString("headimgurl"));
            user.setSex(jsonStr.getInteger("sex"));
            user.setStatus(true);
            user.setOpenid(openid);
            userInfo.setIxmLoginStatus(false);
        }else {
            if(StringUtils.isEmpty(user.getImgUrl()) && jsonStr != null && jsonStr.containsKey("headimgurl")) {
                user.setImgUrl(jsonStr.getString("headimgurl"));
                this.userService.update(user);
            }

            // 返回I厦门的登入状态
            userInfo.setIxmLoginStatus(user.getIxmLoginStatus());
        }
        // 为I厦门那方便去用户数据
        session.setAttribute(WechatConst.SESSION_USER, user);

        BeanCopier.create(user.getClass(),userInfo.getClass(),false).copy(user,userInfo,null);
        userInfo.setOtherId(openid);
        userInfo.setUserType(user.getUserType());

        String token = IdUtil.simpleUUID();
        userInfo.setTokenId(token);
        userInfo.setOtherId(openid);

        // 用户信息放入Redis
        redisClient.set(token,JSONObject.toJSONString(userInfo), CacheKeyConst.EXPIRETIME);
        return userInfo;
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

    /**
     * 添加用户信息
     */
    @Override
    public int addUserInfo(WeChatUserVO weChatUser) {
        String sign2 = DigestUtils.md5DigestAsHex((weChatUser.getCertificateNo() + InsuranceConst.KEY).getBytes());
        if(!StringUtils.equalsIgnoreCase(weChatUser.getSign(), sign2)){
            throw new CommonException("签名错误");
        }

        Map map = new HashMap();
        map.put("certificateNo", weChatUser.getCertificateNo());
        User user  = this.dao.selectSysUserOne(map);

        if(user == null){
//            BeanCopier.create(weChatUser.getClass(),user.getClass(),false).copy(weChatUser,user,null);
            return userService.insert(new User(weChatUser));
        }else {
            user.setOpenid(weChatUser.getOpenid());
            user.setIxmLoginStatus(weChatUser.getIxmLoginStatus());
            return userService.update(user);
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

        //根据openid判断用户本地数据是否存在
        Map map = new HashMap();
        map.put("openid",openid);
        User sysUser  = this.dao.selectSysUserOne(map);

        String certificateNum = user.getString("certificateNum");

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
            sysUser.setSource(SourceEnums.ixm.getCode().toString());

            this.insert(sysUser);

            // 异步同步用户数据
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ixmUserId",sysUser.getIxmUserId());
            paramMap.put("ixmUserName",sysUser.getIxmUserName());
            paramMap.put("ixmRealNameLevel",sysUser.getIxmRealNameLevel());
            paramMap.put("ixmUserRealName",sysUser.getIxmUserRealName());
            paramMap.put("imgUrl",sysUser.getImgUrl());
            paramMap.put("name",sysUser.getUserName());
            paramMap.put("sex",sysUser.getSex());
            // 登入状态
            paramMap.put("ixmLoginStatus","1");
            paramMap.put("ixmToken","");
            paramMap.put("certificateType",user.getString("certificateType"));
            paramMap.put("certificateNo",sysUser.getCertificateNo());
            paramMap.put("phoneNo",sysUser.getPhone());
            paramMap.put("email",sysUser.getEmail());
            paramMap.put("openid",sysUser.getOpenid());
            paramMap.put("source",sysUser.getSource());
            paramMap.put("age",sysUser.getAge());

            String resultUser= HttpUtil.post(InsuranceConst.BASE_URL+"/weChat/syncLoginUser", paramMap);
            JSONObject JSONObj = JSONObject.parseObject(resultUser);
            if(JSONObj == null || JSONObj.getInteger("code") != 200){
                throw new CommonException("请求保险信息详情失败");
            }

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
        return sysUser;
    }

    /**
     * 绑定用户
     *
     * @param correntUser 当前用户
     * @param phone       电话
     * @return {@link UserInfoVo}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoVo bindingWeChat(UserEntity correntUser, String phone) {
        // 验证成功后绑定手机和openid
        User user = userService.findUserByPhone(phone);
        if(ObjectUtils.isEmpty(user)){
            // 后台无记录，请前往社会指导员认证或联系管理员。
            throw new CommonException("绑定失败，查无此手机号用户");
        }

        // 获取当前用户的openid
        String userInfoStr = redisClient.get(correntUser.getToken());
        JSONObject json = JSONObject.parseObject(userInfoStr);
        user.setOpenid(correntUser.getOtherId());
        user.setNickName(json.getString("nickName"));
        user.setSex(json.getInteger("sex"));
        user.setImgUrl(json.getString("imgUrl"));
        userService.update(user);

        UserInfoVo userInfo = JSONObject.parseObject(userInfoStr,UserInfoVo.class);
        userInfo.setUserName(user.getUserName());
        userInfo.setId(user.getId());
        userInfo.setPhone(user.getPhone());
        userInfo.setUserType(user.getUserType());

        // 回填openid和userId给指导员记录
        Instructor instructor = instructorService.findByPhone(phone);
        if(!ObjectUtils.isEmpty(instructor)){
            instructor.setOpenId(correntUser.getOtherId());
            instructor.setUserId(user.getId());
            instructorService.update(instructor);
        }
        return userInfo;
    }

    public JSONObject getOpenId(String code) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(String.format(accessTokenUrl, appid, secret, code),
                    HttpMethod.GET, null, String.class);
            String result = responseEntity.getBody();
            log.info("GetOpenId Result:{}", result);
            JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
            if (jsonObject.containsKey("errcode")) {
                throw new Exception(jsonObject.getString("errmsg"));
            }
            return jsonObject;
        } catch (Exception ex) {
            log.error("GetOpenId Exception : {}", ex);
            return null;
        }
    }
}
