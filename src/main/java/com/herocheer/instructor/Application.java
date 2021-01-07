package com.herocheer.instructor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chenwf
 * @date 2020/12/17
 */
@SpringBootApplication(scanBasePackages = {"com.herocheer"})
@MapperScan(basePackages = {"com.herocheer.instructor.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
