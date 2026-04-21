package cn.sijay.owl.common.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.common.exceptions.AuthException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;

/**
 * LoginHelper
 *
 * @author sijay
 * @since 2026-04-14
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    public static final String USER_INFO = "userInfo";

    /**
     * @param userInfo 登录用户信息
     */
    public static void login(UserInfo userInfo) {
        StpUtil.login(userInfo.userId());
        StpUtil.getTokenSession().set(USER_INFO, userInfo);
    }

    /**
     * 获取用户
     */
    public static UserInfo getUserInfo() {
        SaSession session = StpUtil.getTokenSession();
        if (ObjectUtils.isEmpty(session)) {
            throw new AuthException("用户登录已过期");
        }
        return session.getModel(USER_INFO, UserInfo.class);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 是否为超级管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isSuperAdmin(Long userId) {
        return Objects.equals(1L, userId);
    }

    /**
     * 是否为超级管理员
     *
     * @return 结果
     */
    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }

    /**
     * 检查当前用户是否已登录
     *
     * @return 结果
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 获取部门ID
     */
    public static String getUsername() {
        return getUserInfo().user().getUsername();
    }
}
