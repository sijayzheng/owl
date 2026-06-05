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
    // 组件地址
    String component,
    // 路由参数：如 {"id": 1, "name": "ry"}
    String query,
    //重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
    String redirect,
    //当一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
    boolean alwaysShow,
    // 其他元素
    Meta meta,
    // 子路由
    List<Route> children
) {

}
