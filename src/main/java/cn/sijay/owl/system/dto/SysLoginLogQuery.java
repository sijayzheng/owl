package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 登录日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysLoginLogQuery (
    Long userId,
    String username,
    String loginIp,
    String location,
    String browser,
    String os,
    boolean succeeded,
    String message,
    // 登录时间范围
    LocalDateTime[] loginTimeRange
){
}
