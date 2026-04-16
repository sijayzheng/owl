package cn.sijay.owl.common.config;

import cn.sijay.owl.common.handler.SseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SseAutoConfiguration
 *
 * @author sijay
 * @since 2026-04-16
 */
@Configuration
public class SseAutoConfiguration {

    @Bean
    public SseHandler sseHandler() {
        return new SseHandler();
    }

    @Bean
    public SseTopicListener sseTopicListener() {
        return new SseTopicListener();
    }

    @Bean
    public SseController sseController(SseHandler sseHandler) {
        return new SseController(sseHandler);
    }

}
