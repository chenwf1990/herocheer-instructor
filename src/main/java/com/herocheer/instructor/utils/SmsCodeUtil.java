package com.herocheer.instructor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.ResponseResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjian
 * @version V1.0
 * @Date 2019-08-20 9:04
 * 短信工具类
 */
public class SmsCodeUtil {

    //获取短信验证码地址
    private static final String SMS_CODE_URL = "https://api.leancloud.cn/1.1/requestSmsCode";

    //短信验证码检验地址
    private static final String VERIFY_SMS_CODE_URL = "https://api.leancloud.cn/1.1/verifySmsCode/CODE?mobilePhoneNumber=MOBILE";

    //短信验证请求头
    static Map<String, Object> headers = new HashMap<String, Object>();

    static {
        headers.put("X-AVOSCloud-Application-Id", "1o6PCGzF6K6zscCXY3YXEQDH");
        headers.put("X-AVOSCloud-Application-Key", "iJvMebaXDITWUskMAc1R8KKc");
        headers.put("Content-Type", "application/json");
    }

    /**
     * 发送验证码到手机
     * @param tel
     * @return
     */
    public static ResponseResult getSmsCode(String tel) {
        JSONObject params = new JSONObject();
        params.put("name", "校园安全管理综合平台");
        params.put("mobilePhoneNumber", tel);

        JSONObject result = HttpUtil.httpPost(SMS_CODE_URL, params, headers, 0);
        System.out.println("发送:" + result);
        if (result != null && result.getString("code").equals("0")) {
            return ResponseResult.ok();
        } else {
            JSONObject error = result.getJSONObject("result");
            return ResponseResult.fail(500,error.getString("error"));
        }
    }

    /**
     * 验证短信验证码
     * @param tel
     * @param code
     * @return
     */
    public static ResponseResult verifySmsCode(String tel, String code) {
        String url = VERIFY_SMS_CODE_URL.replace("CODE", code).replace("MOBILE", tel);
        JSONObject result = HttpUtil.httpPost(url, null, headers);
        System.out.println("验证:" + result);
        if (result != null && result.getString("code").equals("0")) {
            return ResponseResult.ok();
        } else {
            JSONObject error = result.getJSONObject("result");
            return ResponseResult.fail(500,error.getString("error"));
        }
    }

    public static void main(String args[]) {

        ResponseResult  result=verifySmsCode("13774517597", "173900");
//        ResponseResult  result=SmsCodeUtil.getSmsCode("13774517597");
        System.out.println(JSON.toJSONString(result));
    }


}
