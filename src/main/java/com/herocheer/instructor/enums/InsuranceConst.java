package com.herocheer.instructor.enums;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 保险信息常量
 *
 * @author gaorh
 * @create 2021-02-25
 */
@Component
public class InsuranceConst {
    public static final String KEY = "20157576992e4ee9904408ec266724e4";

    public static String BASE_URL;

    /**
     * 消息模板id
     */
    public static String TEMPLATE_ID;

    /**
     *  二维码内容
     */
    public static String QRCODE_URL;

    @Value("${ijs.baseUrl}")
    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }


    @Value("${ijs.templateId}")
    public void setTemplateId(String templateId) {
        TEMPLATE_ID = templateId;
    }

    @Value("${ijs.QrCodeUrl}")
    public void setQrCodeUrl(String QrCodeUrl) {
        QRCODE_URL = QrCodeUrl;
    }
    //  public static final String BASE_URL = "http://192.168.147.133/sports/wechat/api";
    //  public static final String URL = "192.168.100.168:8280";

}
