package cn.sijay.owl.system.dto;

/**
 * 系统菜单实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysMenuQuery(
    String menuName,
    Boolean enabled
) {
}
