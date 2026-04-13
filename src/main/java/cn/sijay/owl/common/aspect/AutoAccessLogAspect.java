package cn.sijay.owl.common.aspect;


import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.enums.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AutoAccessLogAspect
 *
 * @author sijay
 * @since 2026-04-08
 */
@Slf4j
@Aspect
@Component
public class AutoAccessLogAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
        AccessLog annotation = method.getAnnotation(AccessLog.class);
        if (annotation != null) {
            String title = annotation.title();
            OperateType operateType = annotation.operateType();
            // 记录访问日志
            log.info("访问日志 - 模块: {}, 操作: {}, 方法: {}", title, operateType, method.getName());
        }
        return joinPoint.proceed();
    }
}