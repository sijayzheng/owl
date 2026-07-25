package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 在线用户实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysUserOnlineQuery (
    String username,
    String loginIp,
    String loginLocation,
    // 登录时间范围
    LocalDateTime[] loginTimeRange
){
}
