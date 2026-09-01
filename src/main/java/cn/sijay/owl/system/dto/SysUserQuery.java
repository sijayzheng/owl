package cn.sijay.owl.system.dto;

/**
 * 系统用户实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysUserQuery(
    String username,
    String realName,
    String email,
    String phone,
    Boolean enabled
) {
}
