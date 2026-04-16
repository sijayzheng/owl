package cn.sijay.owl.auth.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.auth.entity.Meta;
import cn.sijay.owl.auth.entity.Route;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.auth.util.LoginHelper;
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
                     .filter(sysMenu -> !MenuType.BUTTON.name().equals(sysMenu.getMenuType()))
                     .toList();
        return buildMenus(sysMenuService.buildTree(menus));
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        long id = StpUtil.getLoginIdAsLong();
        userInfo.setUser(sysUserService.getById(id));
        userInfo.setRoles(new String[]{"root"});
        userInfo.setPermissions(new String[]{"*:*:*"});
        return userInfo;
    }

    private List<Route> buildMenus(List<SysMenu> menus) {
        List<Route> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            String name = getRouteName(menu) + menu.getId();
            List<SysMenu> cMenus = menu.getChildren();
            if (CollectionUtils.isNotEmpty(cMenus) && MenuType.DIRECTORY.name().equals(menu.getMenuType())) {
                routers.add(new Route(
                    name,
                    getRouterPath(menu),
                    !menu.getVisible(),
                    "noRedirect",
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
                    "",
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    null,
                    List.of(new Route(
                            StringUtils.capitalize(menu.getPath()) + menu.getId(),
                            menu.getPath(),
                            false,
                            null,
                            menu.getComponent(),
                            menu.getQueryParam(),
                            new Meta(menu.getMenuName(), menu.getIcon(), !menu.getCached(), menu.getPath()),
                            null
                        )
                    )
                ));
            } else if (menu.getParentId().equals(0L) && isInnerLink(menu)) {
                String routerPath = innerLinkReplaceEach(menu.getPath());
                String innerLinkName = StringUtils.capitalize(routerPath) + menu.getId();
                routers.add(new Route(
                    name,
                    "/",
                    !menu.getVisible(),
                    "",
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    new Meta(menu.getMenuName(), menu.getIcon(), true, null),
                    List.of(
                        new Route(
                            innerLinkName,
                            routerPath,
                            false,
                            "",
                            "InnerLink",
                            "",
                            new Meta(menu.getMenuName(), menu.getIcon(), false, menu.getPath()),
                            null
                        )
                    )
                ));
            } else {
                routers.add(new Route(
                    getRouteName(menu) + menu.getId(),
                    getRouterPath(menu),
                    !menu.getVisible(),
                    "",
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
    private String getRouteName(SysMenu menu) {
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
    private String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (!Objects.equals(0L, menu.getParentId()) && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (Objects.equals(0L, menu.getParentId()) && MenuType.DIRECTORY.name().equals(menu.getMenuType())
            && menu.getForeignLink()) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     */
    private String getComponentInfo(SysMenu menu) {
        String component = "Layout";
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && !Objects.equals(0L, menu.getParentId()) && isInnerLink(menu)) {
            component = "InnerLink";
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = "ParentView";
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     */
    private boolean isMenuFrame(SysMenu menu) {
        return Objects.equals(0L, menu.getParentId()) && MenuType.MENU.name().equals(menu.getMenuType()) && !menu.getForeignLink();
    }

    /**
     * 是否为内链组件
     */
    private boolean isInnerLink(SysMenu menu) {
        return !menu.getForeignLink() && StringUtil.isHttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     */
    private boolean isParentView(SysMenu menu) {
        return !Objects.equals(0L, menu.getParentId()) && MenuType.DIRECTORY.name().equals(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     */
    private static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{"www.", "http://", "https://", ".", ":"},
            new String[]{"", "", "", "/", "/"});
    }
}

