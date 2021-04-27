package com.herocheer.instructor.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.SysUserVO;
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
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.instructor.utils.IDSAPIClient;
import com.herocheer.instructor.utils.IDSAPIEncryptUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import com.trs.idm.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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

    @Value("${ixmApp.authUrl}")
    private String ixmAppAuthUrl;
    @Value("${ixmApp.client_id}")
    private String ixmAppClientId;
    @Value("${ixmApp.client_secret}")
    private String ixmAppClientSecret;
    @Value("${ixmApp.restUrl}")
    private String ixmAppRestUrl;

    @Value("${smk.userUrl}")
    private String userUrl;
    @Value("${smk.clientid}")
    private String clientid;
    @Value("${smk.password}")
    private String password;

    @Resource
    private RedisClient redisClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private InstructorService instructorService;

    //验证微信交互是否正常
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
        log.debug("微信用户openid：{}",openid);
        // 获取微信群众信息，方便统计用户及展示个人中心信息显示
        JSONObject jsonStr = new JSONObject();
        UserInfoVo userInfo = new UserInfoVo();
        String token = IdUtil.simpleUUID();
        if (StringUtils.isNotEmpty(code)) {
            JSONObject JSONObj = getOpenId(code);
            if (ObjectUtils.isEmpty(JSONObj) || StringUtils.isBlank(JSONObj.getString("openid"))) {
                throw new CommonException("openid未获取成功");
            }
            openid = JSONObj.getString("openid");
            jsonStr  = getWeChatUserInfo(JSONObj);
        }else {
            // 用户出去后，再进来
            String userInfoStr = redisClient.get(openid);
            jsonStr = JSONObject.parseObject(userInfoStr);
            // 之前未给过code情况
            if(ObjectUtils.isEmpty(jsonStr)){
                userInfo.setOtherId(openid);
                userInfo.setCodeFlag(false);
                userInfo.setTokenId(token);
                // 用户信息放入Redis
                redisClient.set(token,JSONObject.toJSONString(userInfo), CacheKeyConst.EXPIRETIME);
                return userInfo;
            }
        }

        // 根据openid获取用户信息
        Map map = new HashMap();
        map.put("openid", openid);
        User user  = this.dao.selectSysUserOne(map);

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
            // 退出第二次登入，个人头像没了
            if(StringUtils.isEmpty(user.getImgUrl()) && jsonStr != null && jsonStr.containsKey("headimgurl")) {
                user.setImgUrl(jsonStr.getString("headimgurl"));
                this.userService.update(user);
            }

            // 返回I厦门的登入状态
            userInfo.setIxmLoginStatus(user.getIxmLoginStatus());
        }
        // 为I厦门那方便取用户数据
        session.setAttribute(WechatConst.SESSION_USER, user);

        BeanCopier.create(user.getClass(),userInfo.getClass(),false).copy(user,userInfo,null);
        userInfo.setUserType(user.getUserType());

        userInfo.setTokenId(token);
        userInfo.setOtherId(openid);
        log.debug("微信用户登入信息：{}",userInfo);
        // 用户信息放入Redis
        redisClient.set(token,JSONObject.toJSONString(userInfo), CacheKeyConst.EXPIRETIME);
        // 用户信息放入缓存，以便于用户第二次进来后可以获取微信用户信息
        redisClient.set(openid,JSONObject.toJSONString(userInfo), CacheKeyConst.OPENID_EXPIRETIME);
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
        map.put("certificateNo", AesUtil.encrypt(weChatUser.getCertificateNo()));
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


    /**
     * ixm登录
     *
     * @param request      请求
     * @param session      会话
     * @param openid       openid
     * @param token        令牌
     * @param currentUser 当前系统的用户信息
     * @return {@link User}
     */
    @Override
    public User ixmLogin(HttpServletRequest request, HttpSession session, String openid, String token,UserInfoVo currentUser) {
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

        //根据phone判断用户本地数据是否存在
        Map map = new HashMap();
        map.put("phone",AesUtil.encrypt(user.getString("mobile")));

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
            // 身份证加密
            sysUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            sysUser.setIxmRealNameLevel(user.getString("realNameStatus"));
            sysUser.setIxmUserRealName(user.getString("certificateName"));
            sysUser.setPhone(AesUtil.encrypt(user.getString("mobile")));
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
            userService.asynUserInfo2Ijianshen(user, sysUser);
        } else {
            User oldUser = User.builder().build();
            oldUser.setId(sysUser.getId());
            oldUser.setIxmUserId(user.getString("uuid").replace("-", ""));
            oldUser.setIxmUserName(user.getString("userName"));
            oldUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            oldUser.setIxmRealNameLevel(user.getString("realNameStatus"));
            oldUser.setUserName(user.getString("certificateName"));
            oldUser.setPhone(AesUtil.encrypt(user.getString("mobile")));
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
            oldUser.setIxmUserRealName(user.getString("certificateName"));

            this.update(oldUser);
            currentUser.setUserType(userService.get(sysUser.getId()).getUserType());

            sysUser.setCertificateNo(certificateNum);
            sysUser.setUserName(user.getString("certificateName"));
            sysUser.setPhone(user.getString("mobile"));
            sysUser.setOpenid(openid);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUpdateTime(oldUser.getUpdateTime());
        }
        /**
         * 重置当前用户信息
         *
         * 业务场景：1、游客进入公众号，在I厦门登入后，需要重新退出再进入公众号，才能获取当前用户信息的场景
         *         2、游客进入公众号，做指导员认证，审批在通过后，在I厦门登入后，需要重新退出再进入公众号，才能获取当前用户信息的场景
         * @Date 2021/3/17 15:27
         **/
        currentUser.setId(sysUser.getId());
        currentUser.setIxmLoginStatus(true);
        log.debug("在I厦门公众号登入后重置当前用户信息：{}",currentUser);
        log.debug("在I厦门公众号登入后重置当前用户信息ID：{}",sysUser.getId());
        redisClient.set(currentUser.getTokenId(),JSONObject.toJSONString(currentUser), CacheKeyConst.EXPIRETIME);

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

    /**
     * 微信列表
     *
     * @param sysUserVO 系统用户签证官
     * @return {@link Page <User>}
     */
    @Override
    public Page<User> findWeChatUserByPage(SysUserVO sysUserVO) {
        Page page = Page.startPage(sysUserVO.getPageNo(), sysUserVO.getPageSize());
        List<User> sysUserList = this.dao.selectWeChatUserByPage(sysUserVO);;
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 发送微信消息
     *
     * @param userList 订阅课程的用户名单
     * @param title    标题
     */
    @Async
    @Override
    public void sendWechatMessages(List<String> userList,String title) {
        // 每日access_token次数有限：每日限额：2000次
        String key = StrUtil.format(CacheKeyConst.ACCESSTOKEN, appid, secret);
        String accessToken = null;
        if (!redisClient.hasKey(key)) {
//             获取I健身的access_token（正式环境）
            String result = findJsapiTicket();
            JSONObject json = JSONObject.parseObject(result);
            accessToken = json.getString("token");

            // 微信公众号配置(测试环境)
//            String appid = "wxf6c55bc2f36ca69b";
//            String secret = "0f7eceb412da0a68b533ad303774da89";
//            String result = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
//            JSONObject JSONObj = JSONObject.parseObject(result);
//            accessToken = JSONObj.getString("access_token");

            if(StringUtils.isBlank(accessToken)){
                throw new CommonException("获取公众号accessToken失败");
            }
            // 失效时间为2个小时
            redisClient.set(key,accessToken,CacheKeyConst.ACCESSTOKEN_EXPIRETIME);
        }else {
            accessToken  = redisClient.get(key);
        }

        JSONObject sendData = null;
        for (String openid : userList){
            sendData = new JSONObject();
            // 报名某课程的用户
            sendData.put("touser", openid);
            // 固定的tempelteId
//            sendData.put("template_id", "403SUrqcDVJF5ICyxExtmEWWK13p0jhKJhPqeBnFdBs");
            sendData.put("template_id", InsuranceConst.TEMPLATE_ID);
            JSONObject content = new JSONObject();
            JSONObject first = new JSONObject();
            first.put("value", StrUtil.format("您好，您之前报名的”{}“已取消，给你造成不便，敬请谅解！",title));
            content.put("first", first);

            JSONObject keyword1 = new JSONObject();
            // 当前时间
            keyword1.put("value", DateUtil.now());
            content.put("keyword1", keyword1);

            JSONObject keyword2 = new JSONObject();
            keyword2.put("value", "熙重电子");
            content.put("keyword2", keyword2);

            JSONObject remark = new JSONObject();
            remark.put("value", "");
            content.put("remark", remark);
            sendData.put("data",content);

            log.info("课程下架消息内容:{}",JSONObject.toJSONString(sendData));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<JSONObject> entity = new HttpEntity<>(sendData, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ accessToken, entity, JSONObject.class);
            // 发送失败时要提醒
            JSONObject jsonObject = responseEntity.getBody();
            if (!"ok".equals(jsonObject.getString("errmsg"))) {
                throw new CommonException("消息发送失败：{}",jsonObject);
            }
        }
    }

    @Override
    public String ixmAppLoginUrl(String redirectUri) {
        String url = ixmAppAuthUrl + "/oauth/authorize?client_id=" + ixmAppClientId
                + "&response_type=code&grant_type=authorization_code" +
                "&scope=snsapi_userinfo&redirect_uri=" + redirectUri;
        log.debug("ixmAppLoginUrl:{}", url);
        return url;
    }

    @Override
    public UserInfoVo ixmAppLogin(HttpServletRequest request, HttpSession session, String code, String redirectUri) {
        String authUrl = ixmAppAuthUrl + "/oauth/token?code=" + code + "&client_id=" + ixmAppClientId
                + "&client_secret=" + ixmAppClientSecret +
                "&grant_type=authorization_code&redirect_uri=" + redirectUri;
        log.debug("ixmApp getAccess_token url:{}", authUrl);
        ResponseEntity<String> tokenEntity = restTemplate.postForEntity(authUrl, null, String.class);
        String tokenResult = tokenEntity.getBody();
        log.debug("ixmApp getAccess_token result:{}", tokenResult);
        if (StringUtils.isBlank(tokenResult)) {
            throw new CommonException("i厦门==========获取token失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(tokenResult, JSONObject.class);
        if (!jsonObject.containsKey("access_token") || !jsonObject.containsKey("scope")) {
            throw new CommonException("i厦门==========获取token失败");
        }
        String accessToken = jsonObject.getString("access_token");
        if (StringUtils.isBlank(accessToken)) {
            throw new CommonException("i厦门==========获取token失败");
        }

        String restUrl = ixmAppRestUrl + "/resource/user/userinfo?access_token=" + accessToken;
        log.debug("ixmApp getUserInfo url:{}", restUrl);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(restUrl, null, String.class);
        String result = responseEntity.getBody();
        log.debug("ixmApp getUserInfo result:{}", result);
        if (StringUtils.isBlank(result)) {
            throw new CommonException("i厦门==========获取用户信息失败");
        }
        JSONObject jsonObject1 = JSONObject.parseObject(result, JSONObject.class);
        if (jsonObject1.getInteger("status") != 1) {
            log.error("i厦门==========获取用户信息失败 code: {} message {}",
                    jsonObject1.getString("code"), jsonObject1.getString("message"));
            throw new CommonException("i厦门==========获取用户信息失败");
        }

        jsonObject1 = jsonObject1.getJSONObject("content");//外层
        JSONObject jsonObject2 = jsonObject1.getJSONObject("userAuth");
        log.debug("ixmApp用户信息：{}", jsonObject1);


        //根据phone判断用户本地数据是否存在
        Map map = new HashMap();
        map.put("phone",AesUtil.encrypt(jsonObject1.getString("phone")));
        User sysUser  = this.dao.selectSysUserOne(map);

        String certificateNum = jsonObject2.getString("cardId");
        if (sysUser == null) {
            sysUser = User.builder().build();
            sysUser.setIxmUserId(jsonObject1.getString("id"));
            sysUser.setIxmUserName(jsonObject1.getString("userName"));
            sysUser.setUserName(jsonObject2.getString("realName"));
            if (certificateNum.length() == 18) {//判断证件类型
                if (Integer.parseInt(certificateNum.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sysUser.setSex(2);
                } else {
                    sysUser.setSex(1);
                }
            } else {
                sysUser.setSex(0);
            }
            // 身份证加密
            sysUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            sysUser.setIxmRealNameLevel(jsonObject2.getString("status"));
            sysUser.setIxmUserRealName(jsonObject2.getString("realName"));
            sysUser.setPhone(AesUtil.encrypt(jsonObject1.getString("phone")));
            sysUser.setInsuranceStatus(0);
            sysUser.setCommitmentStatus(false);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUserType(UserTypeEnums.weChatUser.getCode());
            sysUser.setSource(SourceEnums.ixmApp.getCode().toString());
            this.insert(sysUser);

            // 异步同步用户数据
//            userService.asynUserInfo2Ijianshen(user, sysUser);
        } else {
            User oldUser = User.builder().build();
            oldUser.setId(sysUser.getId());
            oldUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            oldUser.setUserName(jsonObject2.getString("realName"));
            oldUser.setPhone(AesUtil.encrypt(jsonObject1.getString("phone")));
            oldUser.setIxmLoginStatus(true);
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

//            currentUser.setUserType(userService.get(sysUser.getId()).getUserType());

            sysUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            sysUser.setUserName(jsonObject2.getString("realName"));
            sysUser.setPhone(AesUtil.encrypt(jsonObject1.getString("phone")));
//            sysUser.setOpenid(openid);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUpdateTime(oldUser.getUpdateTime());
        }

        sysUser.setIxmToken("");
        //用户信息存入session
        session.setAttribute(WechatConst.SESSION_USER, sysUser);

        UserInfoVo userInfo = new UserInfoVo();
        String token = IdUtil.simpleUUID();
        BeanCopier.create(sysUser.getClass(),userInfo.getClass(),false).copy(sysUser,userInfo,null);
        userInfo.setUserType(sysUser.getUserType());
        userInfo.setTokenId(token);
        userInfo.setOtherId(sysUser.getOpenid());
        log.debug("I厦门APP用户sysUser-OpenId：{}",sysUser.getOpenid());
        log.debug("I厦门APP用户userInfo-OpenId：{}",userInfo.getOtherId());
        log.debug("I厦门APP用户登入信息：{}",userInfo);
        // 用户信息放入Redis
        redisClient.set(token,JSONObject.toJSONString(userInfo), CacheKeyConst.EXPIRETIME);

        return userInfo;
    }

    @Override
    public UserInfoVo smkLogin(HttpSession session, String token) {
        log.debug("smk token:{}", token);
        HttpHeaders headers = new HttpHeaders();
        Long timestamp = System.currentTimeMillis();
        String signature = clientid + ":" + timestamp + ":" + DigestUtils.md5DigestAsHex(password.getBytes());
        String authorization = clientid + ":" + timestamp + ":" + DigestUtils.md5DigestAsHex(signature.getBytes());
        headers.add("Authorization", "UMS01 " + authorization);
        Map<String, Object> param = new HashMap<>();
        param.put("token", token);
        param.put("sceneType ", "00"); // -00 全部  -01 旅游年卡 -02 诊间支付 -03 图书馆
        HttpEntity entity = new HttpEntity(param, headers);
        log.debug("smk requestParam:{}", entity.toString());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(userUrl, entity, String.class);
        String result = responseEntity.getBody();
        log.debug("smk getUserResult:{}", result);
        if (result.equals("Authorization required.")) {
            throw new CommonException("未授权");
        }
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
        if (jsonObject.getInteger("respCode") != 200) {
            throw new CommonException("jsonObject.getString(\"errMsg\")");
        }

        //根据phone判断用户本地数据是否存在
        Map map = new HashMap();
        map.put("phone",AesUtil.encrypt(jsonObject.getString("mobile")));
        User sysUser  = this.dao.selectSysUserOne(map);

        String certificateNum = jsonObject.getString("certifId");
        if (sysUser == null) {
            sysUser = User.builder().build();
            sysUser.setIxmUserId(jsonObject.getString("userUid"));
            sysUser.setIxmUserName(jsonObject.getString("userName"));
            sysUser.setUserName(jsonObject.getString("userRealName"));
            if (certificateNum.length() == 18) {//判断证件类型
                if (Integer.parseInt(certificateNum.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sysUser.setSex(2);
                } else {
                    sysUser.setSex(1);
                }
            } else {
                sysUser.setSex(0);
            }
            // 身份证加密
            sysUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            sysUser.setIxmRealNameLevel(jsonObject.getString("realNameLevel"));
            sysUser.setIxmUserRealName(jsonObject.getString("userRealName"));
            sysUser.setPhone(AesUtil.encrypt(jsonObject.getString("mobile")));
            sysUser.setInsuranceStatus(0);
            sysUser.setCommitmentStatus(false);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUserType(UserTypeEnums.weChatUser.getCode());
            sysUser.setSource(SourceEnums.smk.getCode().toString());
            this.insert(sysUser);

            // 异步同步用户数据
//            userService.asynUserInfo2Ijianshen(user, sysUser);
        } else {
            User oldUser = User.builder().build();
            oldUser.setId(sysUser.getId());
            oldUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            oldUser.setUserName(jsonObject.getString("userRealName"));
            oldUser.setPhone(AesUtil.encrypt(jsonObject.getString("mobile")));
            oldUser.setIxmLoginStatus(true);
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

//            currentUser.setUserType(userService.get(sysUser.getId()).getUserType());

            sysUser.setCertificateNo(AesUtil.encrypt(certificateNum));
            sysUser.setUserName(jsonObject.getString("userRealName"));
            sysUser.setPhone(AesUtil.encrypt(jsonObject.getString("mobile")));
//            sysUser.setOpenid(openid);
            sysUser.setIxmLoginStatus(true);
            sysUser.setUpdateTime(oldUser.getUpdateTime());
        }

        sysUser.setIxmToken("");
        //用户信息存入session
        session.setAttribute(WechatConst.SESSION_USER, sysUser);

        UserInfoVo userInfo = new UserInfoVo();
        String instructorToken = IdUtil.simpleUUID();
        BeanCopier.create(sysUser.getClass(),userInfo.getClass(),false).copy(sysUser,userInfo,null);
        userInfo.setUserType(sysUser.getUserType());
        userInfo.setTokenId(instructorToken);
        userInfo.setOtherId(sysUser.getOpenid());
        log.debug("市民卡APP用户SysUser-OpenId：{}",sysUser.getOpenid());
        log.debug("市民卡APP用户UserInfo-OpenId：{}",userInfo.getOtherId());

        // 用户信息放入Redis
        redisClient.set(instructorToken,JSONObject.toJSONString(userInfo), CacheKeyConst.EXPIRETIME);
        return userInfo;
    }
}