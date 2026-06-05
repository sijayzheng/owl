package cn.sijay.owl.auth.service;

import cn.sijay.owl.auth.entity.Meta;
import cn.sijay.owl.auth.entity.Route;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.auth.utils.LoginHelper;
import cn.sijay.owl.common.enums.MenuType;
import cn.sijay.owl.common.utils.StringUtil;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.service.SysMenuService;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.sijay.owl.system.entity.table.SysMenuTableDef.SYS_MENU;
import static cn.sijay.owl.system.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static cn.sijay.owl.system.entity.table.SysRoleTableDef.SYS_ROLE;
import static cn.sijay.owl.system.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;

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

    public UserInfo getUserInfo() {
        return LoginHelper.getUserInfo();
    }

    public List<Route> getRoutes() {
        if (!LoginHelper.isLogin()) {
            return Collections.emptyList();
        }
        List<SysMenu> menus;
        if (LoginHelper.isSuperAdmin()) {
            menus = sysMenuService.list();
        } else {
            menus = sysMenuService.list(QueryWrapper.create()
                                                    .select(QueryMethods.distinct(SYS_MENU.ALL_COLUMNS))
                                                    .leftJoin(SYS_ROLE_MENU).on(SYS_MENU.ID.eq(SYS_ROLE_MENU.MENU_ID))
                                                    .leftJoin(SYS_ROLE).on(SYS_ROLE.ID.eq(SYS_ROLE_MENU.ROLE_ID))
                                                    .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
                                                    .where(SYS_USER_ROLE.USER_ID.eq(LoginHelper.getUserId()))
                                                    .and(SYS_MENU.MENU_TYPE.in(MenuType.DIRECTORY, MenuType.MENU))
                                                    .and(SYS_MENU.ENABLED.eq(true))
                                                    .and(SYS_ROLE.ENABLED.eq(true))
                                                    .orderBy(
                                                        SYS_MENU.PARENT_ID.asc(),
                                                        SYS_MENU.SORT.asc()
                                                    ));
        }
        return buildMenus(build(menus, 0L, SysMenu::getParentId, (menu, nodeTreeMaps) -> {
            // 将当前节点的菜单ID用作父节点ID
            Long menuParentId = menu.getId();
            // 从动态规划表中取出子节点列表
            // 如果不存在子节点，则返回一个空的列表，确保数据在进行JSON序列化时该字段的类型和结构是正确的
            List<SysMenu> childMenus = nodeTreeMaps.getOrDefault(menuParentId, Collections.emptyList());
            // 设置子节点
            // 如果存在根节点指向尾节点的情况，则会出现环形依赖。但在菜单表中基本不会出现这种情况...
            menu.setChildren(childMenus);
        }));
    }

    public List<Route> buildMenus(List<SysMenu> menus) {
        List<Route> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            List<SysMenu> cMenus = menu.getChildren();
            if (CollectionUtils.isNotEmpty(cMenus) && MenuType.DIRECTORY.equals(menu.getMenuType())) {
                routers.add(new Route(
                        getRouteName(menu),
                        getRouterPath(menu),
                        getComponentInfo(menu),
                        menu.getQueryParam(),
                        "noRedirect",
                        true,
                        new Meta(menu.getMenuName(), menu.getIcon(), !menu.isCached(), getLink(menu), !menu.isVisible(), getActiveMenu(menu)),
                        buildMenus(cMenus)
                    )
                );
            } else if (isMenuFrame(menu)) {
                String frameName = StringUtils.capitalize(menu.getPath()) + menu.getId();
                List<Route> childrenList = new ArrayList<>();
                childrenList.add(new Route(
                    frameName,
                    menu.getPath(),
                    menu.getComponent(),
                    menu.getQueryParam(),
                    null,
                    false,
                    new Meta(menu.getMenuName(), menu.getIcon(), !menu.isCached(), getLink(menu), false, getActiveMenu(menu)),
                    null
                ));
                routers.add(new Route(
                    getRouteName(menu),
                    getRouterPath(menu),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    null,
                    false,
                    null,
                    childrenList
                ));
            } else if (menu.getParentId().equals(0L) && isInnerLink(menu)) {
                List<Route> childrenList = new ArrayList<>();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                childrenList.add(new Route(
                    StringUtils.capitalize(routerPath) + menu.getId(),
                    routerPath,
                    "InnerLink",
                    null,
                    null,
                    false,
                    new Meta(menu.getMenuName(), menu.getIcon(), false, menu.getPath(), false, null),
                    null
                ));
                routers.add(new Route(
                    getRouteName(menu),
                    "/",
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    null,
                    false,
                    new Meta(menu.getMenuName(), menu.getIcon(), false, null, !menu.isVisible(), null),
                    childrenList
                ));
            } else {
                routers.add(new Route(
                    getRouteName(menu),
                    getRouterPath(menu),
                    getComponentInfo(menu),
                    menu.getQueryParam(),
                    null,
                    false,
                    new Meta(menu.getMenuName(), menu.getIcon(), !menu.isCached(), getLink(menu), !menu.isVisible(), getActiveMenu(menu)),
                    null
                ));
            }
        }
        return routers;
    }

    public <K, T> List<T> build(List<T> items, K parentId, Function<T, K> classifier, BiConsumer<T, Map<K, List<T>>> action) {
        // 构建动态规划表 (依据父ID分组)
        Map<K, List<T>> nodeTreeMaps = items.stream().collect(Collectors.groupingBy(classifier));
        // 回溯构建各级节点关系
        items.forEach(item -> action.accept(item, nodeTreeMaps));
        return nodeTreeMaps.get(parentId);
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
        return routerName + menu.getId();
    }

    /**
     * 获取路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (!Objects.equals(0L, menu.getParentId()) && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (Objects.equals(0L, menu.getParentId()) && MenuType.DIRECTORY.equals(menu.getMenuType()) && !menu.isForeignLink()) {
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
    public String getComponentInfo(SysMenu menu) {
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
    public boolean isMenuFrame(SysMenu menu) {
        return Objects.equals(0L, menu.getParentId()) && MenuType.MENU.equals(menu.getMenuType()) && !menu.isForeignLink();
    }

    /**
     * 是否为内链组件
     */
    public boolean isInnerLink(SysMenu menu) {
        return !menu.isForeignLink() && StringUtil.isUrl(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     */
    public boolean isParentView(SysMenu menu) {
        return !Objects.equals(0L, menu.getParentId()) && MenuType.DIRECTORY.equals(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{"http://", "https://", "www.", ".", ":"}, new String[]{"", "", "", "/", "/"});
    }

    public String getActiveMenu(SysMenu menu) {
        String activeMenu = menu.getActiveMenu();
        if (Strings.CI.startsWithAny(activeMenu, "/")) {
            return activeMenu;
        }
        return null;
    }

    public String getLink(SysMenu menu) {
        String link = menu.getPath();
        if (StringUtil.isUrl(link)) {
            return link;
        }
        return null;
    }
}
