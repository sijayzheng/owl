package cn.sijay.owl.common.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.auth.utils.LoginHelper;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.constants.CommonConstants;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.HttpUtil;
import cn.sijay.owl.common.utils.JsonUtil;
import cn.sijay.owl.common.utils.SpringUtil;
import cn.sijay.owl.log.entity.LogAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * AutoAccessLogAspect
 *
 * @author sijay
 * @since 2026-04-08
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class AutoAccessLogAspect {

    /**
     * 排除敏感属性字段
     */
    static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};
    /**
     * 计时 key
     */
    static final ThreadLocal<StopWatch> KEY_CACHE = new ThreadLocal<>();

    /**
     * 处理完请求后执行
     *
     * @param point 切点
     */
    @AfterReturning(pointcut = "@annotation(accessLog)", returning = "result")
    public void doAfterReturning(JoinPoint point, AccessLog accessLog, Object result) {
        handleLog(point, accessLog, result, null);
    }

    /**
     * 拦截异常操作
     *
     * @param point 切点
     * @param e     异常
     */
    @AfterThrowing(value = "@annotation(accessLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint point, AccessLog accessLog, Exception e) {
        handleLog(point, accessLog, "", e);
    }

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = ((org.aspectj.lang.reflect.MethodSignature) point.getSignature()).getMethod();
        AccessLog annotation = method.getAnnotation(AccessLog.class);
        if (annotation != null) {
            String title = annotation.title();
            OperateType operateType = annotation.operateType();
            // 记录访问日志
            log.info("访问日志 - 模块: {}, 操作: {}, 方法: {}", title, operateType, method.getName());
        }
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = request.getRequestURI();
        Signature signature = point.getStaticPart().getSignature();
        String params = JsonUtil.toJson(Arrays.stream(point.getArgs())
                                              .filter(arg -> !(arg instanceof HttpServletResponse || arg instanceof HttpServletRequest || arg instanceof MultipartFile))
                                              .toList());
        log.info("开始请求 => URL【{}】,参数为:【{}】，调用方法：【{}】", url, params, signature.getDeclaringTypeName() + CommonConstants.DOT + signature.getName() + "()");
        return point.proceed();
    }

    void handleLog(final JoinPoint point, AccessLog accessLog, Object result, final Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = request.getRequestURI();
        Signature signature = point.getStaticPart().getSignature();
        String params = JsonUtil.toJson(Arrays.stream(point.getArgs())
                                              .filter(arg -> !(arg instanceof HttpServletResponse || arg instanceof HttpServletRequest || arg instanceof MultipartFile))
                                              .toList());
        String method = signature.getDeclaringTypeName() + CommonConstants.DOT + signature.getName() + "()";
        LogAccess logAccess = new LogAccess();
        if (StpUtil.isLogin()) {
            logAccess.setUserId(LoginHelper.getUserId());
            logAccess.setAccessUsername(LoginHelper.getUsername());
        }
        logAccess.setTitle(accessLog.title());//模块标题
        logAccess.setOperateType(accessLog.operateType());//业务类型
        logAccess.setMethod(method);//方法名称
        logAccess.setRequestMethod(request.getMethod());//请求方式
        logAccess.setAccessUrl(url);//请求url
        String ip = HttpUtil.getIp(request);
        logAccess.setAccessIp(ip);//主机地址
        logAccess.setAccessLocation(HttpUtil.getRegion(ip));//访问地点
        logAccess.setAccessParam(params);//请求参数
        logAccess.setAccessTime(LocalDateTime.now());//访问时间
        if (e != null) {
            logAccess.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());//访问状态
            logAccess.setErrorMsg(e.getMessage());//错误消息
        } else {
            String returnResult = ObjectUtils.isEmpty(result) ? "" : JsonUtil.toJson(result);
            logAccess.setJsonResult(returnResult);//返回参数
            log.info("请求结束 => URL【{}】,请求结果为【{}】", url, returnResult);
            logAccess.setStatus(HttpStatus.OK.value());
        }
        try {
            // 设置方法名称
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            // 设置消耗时间
            StopWatch stopWatch = KEY_CACHE.get();
            stopWatch.stop();
            logAccess.setCostTime(stopWatch.getDuration().toSeconds());//消耗时间
            // 发布事件保存数据库
            SpringUtil.getApplicationContext().publishEvent(logAccess);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
        } finally {
            KEY_CACHE.remove();
        }
    }


    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(accessLog)")
    public void doBefore(AccessLog accessLog) {
        StopWatch stopWatch = new StopWatch();
        KEY_CACHE.set(stopWatch);
        stopWatch.start();
    }

}
