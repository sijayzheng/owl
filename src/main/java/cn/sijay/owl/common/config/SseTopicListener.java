package cn.sijay.owl.common.config;

import cn.sijay.owl.common.handler.SseHandler;
import com.mybatisflex.core.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * SseTopicListener
 *
 * @author sijay
 * @since 2026-04-16
 */
@Slf4j
public class SseTopicListener implements ApplicationRunner {

    @Autowired
    private SseHandler sseHandler;

    /**
     * 在Spring Boot应用程序启动时初始化SSE主题订阅监听器
     *
     * @param args 应用程序参数
     */
    @Override
    public void run(ApplicationArguments args) {
        sseHandler.subscribeMessage((message) -> {
            log.info("SSE主题订阅收到消息session keys={} message={}", message.getUserIds(), message.getMessage());
            // 如果key不为空就按照key发消息 如果为空就群发
            if (CollectionUtil.isNotEmpty(message.getUserIds())) {
                message.getUserIds().forEach(key -> sseHandler.sendMessage(key, message.getMessage()));
            } else {
                sseHandler.sendMessage(message.getMessage());
            }
        });
        log.info("初始化SSE主题订阅监听器成功");
    }

}

