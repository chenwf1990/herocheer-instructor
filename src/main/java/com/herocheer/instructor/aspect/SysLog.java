package com.herocheer.instructor.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志
 *
 * @author gaorh
 * @date 2021/01/20 11:01:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 操作模块
     *
     * @return {@link String}
     */
    String module() default "";

    /**
     * 操作类型
     *
     * @return {@link String}
     */
    String bizType() default "";

    /**
     * 操作说明
     *
     * @return {@link String}
     */
    String bizDesc() default "";
}
