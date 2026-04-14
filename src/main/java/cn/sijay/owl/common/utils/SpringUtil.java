package cn.sijay.owl.common.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
@RequiredArgsConstructor
@Component
public class SpringUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    public static ApplicationContext context() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
