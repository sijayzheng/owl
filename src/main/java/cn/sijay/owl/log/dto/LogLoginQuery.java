package cn.sijay.owl.log.dto;

import java.time.LocalDateTime;

/**
 * 登录日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record LogLoginQuery(
    String username,
    // 登录时间范围
    LocalDateTime[] loginTimeRange
) {
}
