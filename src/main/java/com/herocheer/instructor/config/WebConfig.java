package com.herocheer.instructor.config;

import com.herocheer.web.config.WebAppConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/18
 * @company 厦门熙重电子科技有限公司
 */
@Configuration
public class WebConfig extends WebAppConfigurer {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
}
