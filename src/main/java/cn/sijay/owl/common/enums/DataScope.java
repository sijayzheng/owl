package cn.sijay.owl.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DataScope
 *
 * @author sijay
 * @since 2026/4/13
 */
@Getter
@AllArgsConstructor
public enum DataScope {
    ALL("全部数据权限"),
    CUSTOM("自定义数据权限"),
    DEPT_ONLY("部门数据权限"),
    DEPT_AND_CHILD("部门及以下数据权限"),
    SELF("仅本人数据权限");

    private final String description;
}