package cn.sijay.owl.system.dto;

/**
 * 系统部门实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysDeptQuery(
    Long parentId,
    String deptName,
    Boolean enabled
) {
}
