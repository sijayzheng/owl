package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 系统消息实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysMessageQuery (
    String messageTitle,
    String messageType,
    Long sender,
    Long recipient,
    boolean hasRead
){
}
