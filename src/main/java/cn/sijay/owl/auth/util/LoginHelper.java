package cn.sijay.owl.auth.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.sijay.owl.auth.entity.LoginUser;
import cn.sijay.owl.common.exceptions.AuthException;
import cn.sijay.owl.common.utils.ConvertUtil;
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

    public static final String LOGIN_USER = "loginUser";
    public static final String USER_NAME = "username";
    public static final String DEPT_ID = "deptId";
    public static final String DEPT_NAME = "deptName";

    /**
     * @param loginUser 登录用户信息
     */
    public static void login(LoginUser loginUser) {
        StpUtil.login(loginUser.getId(), new SaLoginParameter()
            .setExtra(USER_NAME, loginUser.getUsername())
            .setExtra(DEPT_ID, loginUser.getDeptId())
            .setExtra(DEPT_NAME, loginUser.getDeptName()));
        StpUtil.getTokenSession().set(LOGIN_USER, loginUser);
    }

    /**
     * 获取用户
     */
    public static LoginUser getLoginUser() {
        SaSession session = StpUtil.getTokenSession();
        if (ObjectUtils.isEmpty(session)) {
            throw new AuthException("用户登录已过期");
        }
        return session.getModel(LOGIN_USER, LoginUser.class);
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
    public static Long getDeptId() {
        return ConvertUtil.convert(getExtra(DEPT_ID), Long.class);
    }

    /**
     * 获取部门ID
     */
    public static String getUsername() {
        return ConvertUtil.convert(getExtra(USER_NAME), String.class);
    }

    public static String getDeptName() {
        return ConvertUtil.convert(getExtra(DEPT_NAME), String.class);
    }

    private static Object getExtra(String key) {
        return StpUtil.getExtra(key);
    }


}
