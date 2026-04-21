package cn.sijay.owl.auth.service;

import cn.sijay.owl.common.utils.LoginHelper;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.entity.SysRole;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PermissionService
 *
 * @author sijay
 * @since 2026-04-14
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionService {
    private final SysUserService sysUserService;

    /**
     * 获取角色数据权限
     *
     * @param userId 用户id
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(Long userId) {
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            return Collections.singleton("root");
        } else {
            SysUser user = sysUserService.getWithRelations(userId);
            return user.getRoles().parallelStream().map(SysRole::getRoleCode).collect(Collectors.toSet());
        }
    }

    /**
     * 获取菜单数据权限
     *
     * @param userId 用户id
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(Long userId) {
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            return Collections.singleton("*:*:*");
        } else {
            SysUser user = sysUserService.getWithRelations(userId);
            return user.getRoles().parallelStream().map(SysRole::getMenus).flatMap(List::stream).map(SysMenu::getPerms).filter(StringUtils::isNotBlank)
                       .collect(Collectors.toSet());
        }
    }
}
