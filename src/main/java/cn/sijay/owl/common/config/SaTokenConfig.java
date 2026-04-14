package cn.sijay.owl.common.config;


import cn.dev33.satoken.stp.StpInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaTokenConfig
 *
 * @author sijay
 * @since 2026-04-14
 */
@Configuration
public class SaTokenConfig {
    /**
     * 权限接口实现(使用bean注入方便用户替换)
     */
    @Bean
    public StpInterface stpInterface() {
        return new StpInterfaceImpl();
    }

}
