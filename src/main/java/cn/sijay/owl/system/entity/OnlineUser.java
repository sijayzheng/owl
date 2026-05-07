package cn.sijay.owl.system.entity;


import java.time.LocalDateTime;

/**
 * OnlineUser
 *
 * @author sijay
 * @since 2026-05-07
 */
public record OnlineUser(
    String username,
    String deptName,
    String token,
    LocalDateTime loginTime
) {

}
