package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.vo.WechaLoginVo;
import com.herocheer.instructor.domain.vo.WxInfoVO;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
public interface LoginService {
    /**
     * @author chenwf
     * @desc 微信登录
     * @date 2021/1/26
     * @param wechaLoginVo
     */
    void wechatLogin(WechaLoginVo wechaLoginVo);

    void loginTest(String token, Long userId);

}
