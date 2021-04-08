package com.herocheer.instructor.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;
import com.herocheer.instructor.utils.IPUtil;
import com.herocheer.instructor.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志AOP
 *
 * @author gaorh
 * @create 2021-01-20
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param result      返回结果
     */
    @AfterReturning(value = "execution(* com.herocheer.instructor.service.*Service.*(..)) && @annotation(sysLog)", returning = "result")
    public void saveOperLog(JoinPoint joinPoint, SysLog sysLog ,Object result) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperationLogVO sysOperationLog = SysOperationLogVO.builder().status(true).build();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            SysLog sysLogAnnotation = method.getAnnotation(SysLog.class);
            if (sysLogAnnotation != null) {
                // 操作模块
                sysOperationLog.setModule(sysLogAnnotation.module());
                // 操作类型
                sysOperationLog.setBizType(sysLogAnnotation.bizType());
                // 操作描述
                sysOperationLog.setContext(sysLogAnnotation.bizDesc());
            }

            // 请求的参数
            Object[] args = joinPoint.getArgs();
            List<Object> objList = new ArrayList<>();
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile) {
                    continue;
                }
                objList.add(arg);
            }
            sysOperationLog.setRequest(JSON.toJSONString(objList));
            sysOperationLog.setResponse(JSON.toJSONString(result)); // 返回结果
            sysOperationLog.setUri(request.getMethod() +" " +request.getRequestURI()); // 请求URI
            sysOperationLog.setIp(IPUtil.getRealIp(request)); // 请求IP
            sysOperationLog.setIp(ServletUtil.getClientIP(request)); // 请求IP

            // 异步解耦
            SpringUtil.publishEvent(new SysLogEvent(sysOperationLog));
        } catch (Exception e) {
            log.error("系统报错：",e);
        }
    }
}
