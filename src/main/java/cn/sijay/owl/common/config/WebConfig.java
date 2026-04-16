package cn.sijay.owl.common.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.httpauth.basic.SaHttpBasicUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.sijay.owl.auth.entity.LoginUser;
import cn.sijay.owl.auth.service.PermissionService;
import cn.sijay.owl.auth.util.LoginHelper;
import cn.sijay.owl.common.exceptions.BaseException;
import cn.sijay.owl.common.exceptions.GlobalExceptionHandler;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.common.filter.RepeatableFilter;
import cn.sijay.owl.common.filter.XssFilter;
import cn.sijay.owl.common.utils.ServletUtil;
import cn.sijay.owl.common.utils.SpringUtil;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.HibernateValidator;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * WebConfig
 *
 * @author sijay
 * @since 2026-04-14
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter
                        // 获取所有的
                        .match("/**")
                        // 对未排除的路径进行检查
                        .check(() -> {
                            HttpServletRequest request = ServletUtil.getRequest();
                            // 检查是否登录 是否有token
                            try {
                                StpUtil.checkLogin();
                            } catch (NotLoginException e) {
                                if (Objects.requireNonNull(request).getRequestURI().contains("sse")) {
                                    throw new BaseException(e.getMessage(), e.getCode());
                                } else {
                                    throw e;
                                }
                            }
                        });
                }))
                .addPathPatterns("/**")
                // 排除不需要拦截的路径
                .excludePathPatterns("/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/favicon.ico", "/error", "/*/api-docs", "/*/api-docs/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(@NonNull String resourcePath, @NonNull Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource :
                            new ClassPathResource("/templates/index.html");
                    }
                });
    }

    /**
     * 配置校验框架 快速返回模式
     */
    @Bean
    public Validator validator() {
        try (LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean()) {
            // 设置使用 HibernateValidator 校验器
            factoryBean.setProviderClass(HibernateValidator.class);
            Properties properties = new Properties();
            // 设置 快速异常返回
            properties.setProperty("hibernate.validator.fail_fast", "true");
            factoryBean.setValidationProperties(properties);
            // 加载配置
            factoryBean.afterPropertiesSet();
            return factoryBean.getValidator();
        }
    }

    /**
     * 全局异常处理器
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * 权限接口实现(使用bean注入方便用户替换)
     */
    @Bean
    public StpInterface stpInterface() {
        return new StpInterface() {
            @Override
            public List<String> getPermissionList(Object loginId, String loginType) {
                LoginUser loginUser = LoginHelper.getLoginUser();
                if (ObjectUtils.isEmpty(loginUser) || !loginUser.getId().equals(loginId)) {
                    return new ArrayList<>(getPermissionService().getMenuPermission(Long.parseLong(loginId.toString())));
                }
                // SYS_USER 默认返回权限
                return new ArrayList<>(loginUser.getMenuPermission());
            }

            @Override
            public List<String> getRoleList(Object loginId, String loginType) {
                LoginUser loginUser = LoginHelper.getLoginUser();
                if (ObjectUtils.isEmpty(loginUser) || !loginUser.getId().equals(loginId)) {
                    return new ArrayList<>(getPermissionService().getRolePermission(Long.parseLong(loginId.toString())));
                }
                // SYS_USER 默认返回权限
                return new ArrayList<>(loginUser.getRolePermission());
            }

            private PermissionService getPermissionService() {
                try {
                    PermissionService service = SpringUtil.getBean(PermissionService.class);
                    if (ObjectUtils.isNotEmpty(service)) {
                        throw new ServiceException(StpInterface.class, "PermissionService 实现类不存在");
                    }
                    return service;
                } catch (Exception e) {
                    throw new ServiceException(StpInterface.class, "PermissionService 实现类不存在");
                }
            }
        };
    }

    /**
     * 对 actuator 健康检查接口 做账号密码鉴权
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
            .addInclude("/actuator", "/actuator/**")
            .setAuth(obj -> SaHttpBasicUtil.check("root:root"))
            .setError(e -> SaResult.error(e.getMessage()).setCode(HttpStatus.UNAUTHORIZED.value()));
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<RepeatableFilter> someFilterRegistration() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
