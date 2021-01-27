
package com.herocheer.instructor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author chenwf
 * @date 2020/12/17
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.herocheer"},exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@MapperScan(basePackages = {"com.herocheer.instructor.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 将加密工具类加入IOC容器中,便于加密
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
