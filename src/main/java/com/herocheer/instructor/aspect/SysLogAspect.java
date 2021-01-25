package com.herocheer.instructor.aspect;

import com.alibaba.fastjson.JSON;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;
import com.herocheer.instructor.service.SysOperationLogService;
import com.herocheer.instructor.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
//    @Pointcut("@annotation(com.herocheer.instructor.aspect.SysLog)")
//    public void declareJoinPointerExpression() {
//    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
//    @Pointcut("execution(* com.herocheer.instructor.controller..*.*(..))")
//    public void operExceptionLogPoinCut() {
//    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param result      返回结果
     */
    @AfterReturning(value = "execution(* com.herocheer.instructor.service.impl.*ServiceImpl.*(..)) && @annotation(sysLog)", returning = "result")
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
            SysLog SysLog = method.getAnnotation(SysLog.class);
            if (SysLog != null) {
                // 操作模块
                sysOperationLog.setModule(SysLog.module());
                // 操作类型
                sysOperationLog.setBizType(SysLog.bizType());
                // 操作描述
                sysOperationLog.setContext(SysLog.bizDesc());
            }

            // 请求的方法参数值
//            Object[] args = joinPoint.getArgs();
//            // 请求的方法参数名称
//            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//            String[] paramNames = u.getParameterNames(method);
//            if (args != null && paramNames != null) {
//                String params = "";
//                for (int i = 0; i < args.length; i++) {
//                    params += "  " + paramNames[i] + ": " + args[i];
//                }
//                sysOperationLog.setRequest(params);
//            }

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
            sysOperationLogService.addSysOperationLog(sysOperationLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
