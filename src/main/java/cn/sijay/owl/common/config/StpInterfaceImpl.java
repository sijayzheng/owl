package cn.sijay.owl.common.config;


import cn.dev33.satoken.stp.StpInterface;
import cn.sijay.owl.auth.entity.LoginUser;
import cn.sijay.owl.auth.service.PermissionService;
import cn.sijay.owl.auth.util.LoginHelper;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.common.utils.SpringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SaPermissionImpl
 *
 * @author sijay
 * @since 2026-04-14
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || !loginUser.getId().equals(loginId)) {
            PermissionService permissionService = getPermissionService();
            if (ObjectUtils.isNotEmpty(permissionService)) {
                return new ArrayList<>(permissionService.getMenuPermission(Long.parseLong(loginId.toString())));
            } else {
                throw new ServiceException(StpInterfaceImpl.class, "PermissionService 实现类不存在");
            }
        }
        // SYS_USER 默认返回权限
        return new ArrayList<>(loginUser.getMenuPermission());
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || !loginUser.getId().equals(loginId)) {
            PermissionService permissionService = getPermissionService();
            if (ObjectUtils.isNotEmpty(permissionService)) {
                return new ArrayList<>(permissionService.getRolePermission(Long.parseLong(loginId.toString())));
            } else {
                throw new ServiceException(StpInterfaceImpl.class, "PermissionService 实现类不存在");
            }
        }
        // SYS_USER 默认返回权限
        return new ArrayList<>(loginUser.getRolePermission());
    }

    private PermissionService getPermissionService() {
        try {
            return SpringUtil.getBean(PermissionService.class);
        } catch (Exception e) {
            return null;
        }
    }

}

