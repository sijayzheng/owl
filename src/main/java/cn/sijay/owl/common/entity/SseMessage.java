package cn.sijay.owl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * SseMessage
 *
 * @author sijay
 * @since 2026-04-16
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 需要发送的消息
     */
    private String message;
    /**
     * 需要推送到的session key 列表
     */
    private List<Long> userIds;
}
