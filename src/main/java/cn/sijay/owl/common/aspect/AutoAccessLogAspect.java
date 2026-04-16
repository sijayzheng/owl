package cn.sijay.owl.common.aspect;

import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.JsonUtil;
import com.mybatisflex.core.util.ArrayUtil;
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
import org.springdoc.core.utils.Constants;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

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
    @AfterReturning(pointcut = "@annotation(log)", returning = "result")
    public void doAfterReturning(JoinPoint point, AccessLog log, Object result) {
        handleLog(point, log, result, null);
    }

    /**
     * 拦截异常操作
     *
     * @param point 切点
     * @param e     异常
     */
    @AfterThrowing(value = "@annotation(oprLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint point, AccessLog oprLog, Exception e) {
        handleLog(point, oprLog, null, e);
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
        log.info("开始请求 => URL【{}】,参数为:【{}】", url, params);
        String method1 = signature.getDeclaringTypeName() + Constants.DOT + signature.getName() + "()";
        log.info("----{}---", method1);
        return point.proceed();
    }

    void handleLog(final JoinPoint point, AccessLog oprLog, Object result, final Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = request.getRequestURI();
        Signature signature = point.getStaticPart().getSignature();
        String params = JsonUtil.toJson(Arrays.stream(point.getArgs())
                                              .filter(arg -> !(arg instanceof HttpServletResponse || arg instanceof HttpServletRequest || arg instanceof MultipartFile))
                                              .toList());
        log.info("开始请求 => URL【{}】,参数为:【{}】", url, params);
        String method = signature.getDeclaringTypeName() + Constants.DOT + signature.getName() + "()";
        String returnResult = ObjectUtils.isEmpty(result) ? "" : JsonUtil.toJson(result);
//        LogBusiness logBusiness = new LogBusiness().setUserId(1L).setIp(request.getRemoteAddr()).setMethod(method).setParams(params)
//                                                   .setRequestType(request.getMethod()).setRequestUrl(request.getRequestURI())
//                                                   .setBusinessName(oprLog.value()).setOperationType(oprLog.operateType()).setReturnResult(returnResult)
//                                                   .setOperationTime(LocalDateTime.now());
        if (e != null) {
//            logBusiness.setOperationResult(ResultCodeEnum.FAILURE);
//            logBusiness.setErrorMessage(e.getMessage());
        }
//        logBusiness.setOperationResult(ResultCodeEnum.SUCCESS);
//        System.out.println(logBusiness);
        log.info("请求结束 => URL【{}】,请求结果为【{}】", url, returnResult);
        try {
            // *========数据库日志=========*//
//            OpLogEvent operLog = new OpLogEvent();
//            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
//            // 请求的地址
//            operLog.setOperUrl(StringUtil.substring(ServletUtil.getRequest().getRequestURI(), 0, 255));
//            LoginUser loginUser = LoginHelper.getLoginUser();
//            operLog.setOperName(loginUser.getUsername());
//            operLog.setDeptName(loginUser.getDeptName());
//            if (e != null) {
//                operLog.setStatus(BusinessStatus.FAIL.ordinal());
//                operLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
//            }
            // 设置方法名称
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
//            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
//            operLog.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 处理设置注解上的参数
//            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 设置消耗时间
            StopWatch stopWatch = KEY_CACHE.get();
            stopWatch.stop();
//            operLog.setCostTime(stopWatch.getDuration().toSeconds());
            // 发布事件保存数据库
//            SpringUtil.getApplicationContext().publishEvent(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            KEY_CACHE.remove();
        }
    }


    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, AccessLog controllerLog) {
        StopWatch stopWatch = new StopWatch();
        KEY_CACHE.set(stopWatch);
        stopWatch.start();
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
//    public void getControllerMethodDescription(JoinPoint joinPoint, AccessLog log, OpLogEvent operLog, Object jsonResult) throws Exception {
    // 设置action动作
//        operLog.setBusinessType(log.businessType().ordinal());
    // 设置标题
//        operLog.setTitle(log.title());
    // 是否需要保存request，参数和值
//        if (log.isSaveRequestData()) {
//            // 获取参数的信息，传入到数据库中。
//            setRequestValue(joinPoint, operLog, log.excludeParamNames());
//        }
    // 是否需要保存response，参数和值
//        if (log.isSaveResponseData() && !Objects.isNull(jsonResult)) {
//            operLog.setJsonResult(StringUtil.substring(JsonUtil.toJson(jsonResult), 0, 2000));
//        }
//    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
//    private void setRequestValue(JoinPoint joinPoint, OpLogEvent operLog, String[] excludeParamNames) throws Exception {
//        Map<String, String> paramsMap = ServletUtil.getParamMap(ServletUtil.getRequest());
//        String requestMethod = operLog.getRequestMethod();
//        if (MapUtil.isEmpty(paramsMap) && StringUtils.equalsAny(requestMethod, HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name())) {
//            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
//            operLog.setOperParam(StringUtil.substring(params, 0, 2000));
//        } else {
//            MapUtil.removeAny(paramsMap, EXCLUDE_PROPERTIES);
//            MapUtil.removeAny(paramsMap, excludeParamNames);
//            operLog.setOperParam(StringUtil.substring(JsonUtil.toJson(paramsMap), 0, 2000));
//        }
//    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (!Objects.isNull(o) && !isFilterObject(o)) {
                String str = JsonUtil.toJson(o);
//                Dict dict = jsonUtils.parseMap(str);
//                if (MapUtil.isNotEmpty(dict)) {
//                    MapUtil.removeAny(dict, EXCLUDE_PROPERTIES);
//                    MapUtil.removeAny(dict, excludeParamNames);
//                    str = JsonUtil.toJson(dict);
//                }
                params.add(str);
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }
}
