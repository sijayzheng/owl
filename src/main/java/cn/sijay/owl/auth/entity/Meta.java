package cn.sijay.owl.auth.entity;


/**
 * Meta
 *
 * @author sijay
 * @since 2026-04-16
 */
public record Meta(
    // 设置该路由在侧边栏和面包屑中展示的名字
    String title,
    // 图标
    String icon,
    // 设置为true，则不会被 <keep-alive>缓存
    boolean noCache,
    // 内链地址（http(s)://开头）
    String link
) {

}
