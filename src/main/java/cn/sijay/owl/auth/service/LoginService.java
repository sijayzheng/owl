package cn.sijay.owl.auth.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.auth.entity.LoginReq;
import cn.sijay.owl.auth.entity.LoginResp;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.common.constants.RedisPrefix;
import cn.sijay.owl.common.exceptions.AuthException;
import cn.sijay.owl.common.utils.*;
import cn.sijay.owl.log.entity.LogLogin;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.service.SysDeptService;
import cn.sijay.owl.system.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * LoginService
 *
 * @author sijay
 * @since 2026-04-16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final SysUserService sysUserService;
    private final SysDeptService sysDeptService;
    private final PermissionService permissionService;

    public LoginResp login(@Valid LoginReq loginReq) {
        String username = loginReq.username();
        SysUser user = sysUserService.getByUsername(username);
        if (user == null) {
            throw new AuthException("用户名或密码错误");
        }
        if (!user.getEnabled()) {
            throw new AuthException("用户已被禁用");
        }
        boolean check = PasswordUtil.check(loginReq.password(), user.getPassword());
        int maxRetryCount = 5;
        long lockTime = 10;
        String key = RedisPrefix.INCORRECT_PASSWORD_KEY + username;
        Integer errorNumber = RedisUtil.get(key);
        // 锁定时间内登录 则踢出
        Long userId = user.getId();
        if (!check) {
            // 错误次数递增
            errorNumber++;
            RedisUtil.set(key, errorNumber);
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLoginInfo(userId, username, false, "密码输入错误" + maxRetryCount + "次，帐户锁定" + lockTime + "分钟");
                throw new AuthException("密码输入错误" + maxRetryCount + "次，帐户锁定" + lockTime + "分钟");
            } else {
                // 未达到规定错误次数
                recordLoginInfo(userId, username, false, "密码输入错误" + errorNumber + "次");
                throw new AuthException("密码输入错误" + errorNumber + "次");
            }
        }
        // 登录成功 清空错误次数
        RedisUtil.delete(key);
        UserInfo userInfo = new UserInfo(
            userId,
            user,
            permissionService.getMenuPermission(userId),
            permissionService.getRolePermission(userId)
        );
        LoginHelper.login(userInfo);
        recordLoginInfo(userId, username, true, "登录成功");
        return new LoginResp(StpUtil.getTokenValue());
    }

    public void logout() {
        try {
            SysUser user = sysUserService.getById(StpUtil.getLoginIdAsLong());
            if (user == null) {
                throw new AuthException("用户不存在");
            }
            recordLoginInfo(user.getId(), user.getUsername(), true, "退出成功");
        } catch (Exception ignore) {

        }
        StpUtil.logout();
    }

    public void recordLoginInfo(Long id, String username, boolean successful, String msg) {
        LogLogin logLogin = new LogLogin();
        logLogin.setUserId(id);
        logLogin.setUsername(username);
        HttpServletRequest request = ServletUtil.getRequest();
        logLogin.setLoginIp(HttpUtil.getIp(request));
        logLogin.setLocation(HttpUtil.getRegion(logLogin.getLoginIp()));
        logLogin.setBrowser(HttpUtil.getBrowser(request));
        logLogin.setOs(HttpUtil.getOs(request));
        logLogin.setSucceeded(successful);
        logLogin.setMessage(msg);
        logLogin.setLoginTime(LocalDateTime.now());
        SpringUtil.getApplicationContext().publishEvent(logLogin);
    }
}
