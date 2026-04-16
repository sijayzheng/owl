package cn.sijay.owl.common.config;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.auth.util.LoginHelper;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.entity.SseMessage;
import cn.sijay.owl.common.handler.SseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * SseController
 *
 * @author sijay
 * @since 2026-04-16
 */
@RestController
@RequiredArgsConstructor
public class SseController implements DisposableBean {

    private final SseHandler sseHandler;

    /**
     * 建立 SSE 连接
     */
    @GetMapping(value = "/resource/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        String tokenValue = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        return sseHandler.connect(userId, tokenValue);
    }

    /**
     * 关闭 SSE 连接
     */
    @SaIgnore
    @GetMapping(value = "/resource/sse/close")
    public Result<Void> close() {
        String tokenValue = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        sseHandler.disconnect(userId, tokenValue);
        return Result.success();
    }

    /**
     * 向特定用户发送消息
     *
     * @param userId 目标用户的 ID
     * @param msg    要发送的消息内容
     */
    @GetMapping(value = "/resource/sse/send")
    public Result<Void> send(Long userId, String msg) {
        SseMessage dto = new SseMessage();
        dto.setUserIds(List.of(userId));
        dto.setMessage(msg);
        sseHandler.publishMessage(dto);
        return Result.success();
    }

    /**
     * 向所有用户发送消息
     *
     * @param msg 要发送的消息内容
     */
    @GetMapping(value = "/resource/sse/sendAll")
    public Result<Void> send(String msg) {
        sseHandler.publishAll(msg);
        return Result.success();
    }

    /**
     * 清理资源
     */
    @Override
    public void destroy() {
        // 销毁时不需要做什么 此方法避免无用操作报错
    }

}

