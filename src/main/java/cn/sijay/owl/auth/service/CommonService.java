package cn.sijay.owl.auth.service;

import cn.sijay.owl.auth.entity.Meta;
import cn.sijay.owl.auth.entity.Route;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.auth.utils.LoginHelper;
import cn.sijay.owl.common.enums.MenuType;
import cn.sijay.owl.common.utils.StringUtil;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.entity.SysRole;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.service.SysMenuService;
import cn.sijay.owl.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * RouteService
 *
 * @author sijay
 * @since 2026-04-16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final SysMenuService sysMenuService;
    private final SysUserService sysUserService;

    public List<Route> getRoutes() {
        List<SysMenu> menus;
        if (LoginHelper.isSuperAdmin()) {
            menus = sysMenuService.list();
        } else {
            SysUser user = sysUserService.getWithRelations(LoginHelper.getUserId());
            List<SysRole> roles = user.getRoles();
            menus = roles.stream().map(SysRole::getMenus).flatMap(List::stream).toList();
        }
        menus = menus.parallelStream()
                     .filter(SysMenu::getEnabled)
                     .filter(sysMenu -> !MenuType.BUTTON.equals(sysMenu.getMenuType()))
                     .toList();
        return buildMenus(sysMenuService.buildTree(menus));
    }

    public UserInfo getUserInfo() {
        return LoginHelper.getUserInfo();
    }

    /**
     * 构建前端路由所需要的菜单
     * 路由name命名规则 path首字母转大写 + id
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<Route> buildMenus(List<SysMenu> menus) {
        List<Route> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            String name = getRouteName(menu) + menu.getId();

            List<SysMenu> cMenus = menu.getChildren();
            if (CollectionUtils.isNotEmpty(cMenus) && MenuType.DIRECTORY.equals(menu.getMenuType())) {
                routers.add(new Route(
                    name,
                    getRouterPath(menu),
                    !menu.getVisible(),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    new Meta(menu.getMenuName(), menu.getIcon(), !menu.getCached(), menu.getPath()),
                    buildMenus(cMenus)
                ));
            } else if (isMenuFrame(menu)) {
                routers.add(new Route(
                    name,
                    getRouterPath(menu),
                    !menu.getVisible(),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    null,
                    Collections.singletonList(new Route(
                        StringUtils.capitalize(menu.getPath()) + menu.getId(),
                        menu.getPath(),
                        false,
                        menu.getComponent(),
                        menu.getQueryParam(),
                        new Meta(menu.getMenuName(), menu.getIcon(), !menu.getCached(), menu.getPath()),
                        null
                    ))
                ));
            } else if (menu.getParentId().equals(0L) && isInnerLink(menu)) {
                String routerPath = innerLinkReplaceEach(menu.getPath());
                routers.add(new Route(
                    name,
                    "/",
                    !menu.getVisible(),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    new Meta(menu.getMenuName(), menu.getIcon(), false, null),
                    Collections.singletonList(new Route(
                        StringUtils.capitalize(routerPath) + menu.getId(),
                        routerPath,
                        false,
                        "InnerLink",
                        null,
                        new Meta(menu.getMenuName(), menu.getIcon(), false, menu.getPath()),
                        null
                    ))
                ));
            } else {
                routers.add(new Route(
                    name,
                    getRouterPath(menu),
                    !menu.getVisible(),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    new Meta(menu.getMenuName(), menu.getIcon(), !menu.getCached(), menu.getPath()),
                    null
                ));
            }
        }
        return routers;
    }

    /**
     * 获取路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        if (!Objects.equals(0L, menu.getParentId()) && isInnerLink(menu)) {
            // 内链打开外网方式
            routerPath = innerLinkReplaceEach(routerPath);
        }
        if (Objects.equals(0L, menu.getParentId()) && MenuType.DIRECTORY.equals(menu.getMenuType()) && !menu.getForeignLink()) {
            // 非外链并且是一级目录（类型为目录）
            routerPath = "/" + menu.getPath();
        } else if (isMenuFrame(menu)) {
            // 非外链并且是一级目录（类型为菜单）
            routerPath = "/";
        }
        log.info("routerPath: {}", routerPath);
        return routerPath;
    }

    /**
     * 获取组件信息
     */
    public String getComponentInfo(SysMenu menu) {
        String component = "Layout";
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && !Objects.equals(0L, menu.getParentId()) && isInnerLink(menu)) {
            component = "InnerLink";
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     */
    public boolean isMenuFrame(SysMenu menu) {
        return Objects.equals(0L, menu.getParentId()) && MenuType.MENU.equals(menu.getMenuType()) && !menu.getForeignLink();
    }

    /**
     * 是否为内链组件
     */
    public boolean isInnerLink(SysMenu menu) {
        return !menu.getForeignLink() && StringUtil.isUrl(menu.getPath());
    }

    /**
     * 内链域名特殊字符替换
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{"http://", "https://", "www.", ".", ":"}, new String[]{"", "", "", "/", "/"});
    }

}

