package cn.sijay.owl.auth.entity;

import java.util.List;

/**
 * Route
 *
 * @author sijay
 * @since 2026-04-16
 */
public record Route(
    // 路由名字
    String name,
    // 路由地址
    String path,
    // 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
    boolean hidden,
    // 组件地址
    String component,
    // 路由参数：如 {"id": 1, "name": "ry"}
    String query,
    // 其他元素
    Meta meta,
    // 子路由
    List<Route> children
) {

}
