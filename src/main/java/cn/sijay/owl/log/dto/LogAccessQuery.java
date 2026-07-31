package cn.sijay.owl.log.dto;

import java.time.LocalDateTime;

/**
 * 访问日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record LogAccessQuery(
    String accessUsername,
    String accessUrl,
    // 访问时间范围
    LocalDateTime[] accessTimeRange
) {
}
