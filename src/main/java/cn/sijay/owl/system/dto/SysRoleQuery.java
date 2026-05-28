package cn.sijay.owl.system.dto;

/**
 * 系统角色实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysRoleQuery(
    String roleName,
    Boolean enabled
) {
}
