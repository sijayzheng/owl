package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 访问日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysAccessLogQuery (
    Long userId,
    String title,
    String operateType,
    String method,
    String requestMethod,
    String accessUsername,
    String accessUrl,
    String accessIp,
    String accessLocation,
    Integer status,
    // 访问时间范围
    LocalDateTime[] accessTimeRange,
    Long costTime
){
}
