package cn.sijay.owl.common.config;

import cn.sijay.owl.common.aspect.RepeatSubmitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IdempotentConfig
 * 幂等功能配置
 *
 * @author sijay
 * @since 2026-04-16
 */
@Configuration
public class IdempotentConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }

}

