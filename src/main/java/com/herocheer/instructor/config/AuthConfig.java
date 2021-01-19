package com.herocheer.instructor.config;

import com.herocheer.web.config.AuthInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/18
 * @company 厦门熙重电子科技有限公司
 */
@Component
public class AuthConfig extends AuthInterceptor {
    @Override
    protected boolean interceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return super.interceptor(request,response, handler);
    }
}
