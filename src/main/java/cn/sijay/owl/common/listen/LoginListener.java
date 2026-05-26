package cn.sijay.owl.common.listen;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.sijay.owl.auth.service.LoginService;
import cn.sijay.owl.common.constants.RedisPrefix;
import cn.sijay.owl.common.utils.LoginHelper;
import cn.sijay.owl.common.utils.RedisUtil;
import cn.sijay.owl.system.entity.OnlineUser;
import cn.sijay.owl.system.entity.SysDept;
import cn.sijay.owl.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * LoginListener
 *
 * @author sijay
 * @since 2026-04-16
 */
@RequiredArgsConstructor
@Component
@Slf4j
public abstract class LoginListener implements SaTokenListener {
    private final SaTokenConfig tokenConfig;
    private final LoginService loginService;
    private final SysDeptService deptService;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginParameter loginModel) {
        String username = LoginHelper.getUsername();
        String deptName = deptService.getByIdOpt(LoginHelper.getDeptId()).orElse(new SysDept()).getDeptName();
        OnlineUser dto = new OnlineUser(
            username,
            deptName,
            tokenValue,
            LocalDateTime.now()
        );
        if (tokenConfig.getTimeout() == -1) {
            RedisUtil.set(RedisPrefix.ONLINE_TOKEN_KEY + tokenValue, dto);
        } else {
            RedisUtil.set(RedisPrefix.ONLINE_TOKEN_KEY + tokenValue, dto, tokenConfig.getTimeout());
        }
        // 记录登录日志
//        LogLogin logLogin = new LogLogin();
//        logLogin.setUsername(username);
//        logLogin.setSucceeded(true);
//        logLogin.setMessage("登录成功");
//        SpringUtil.context().publishEvent(logLogin);
        // 更新登录信息
        loginService.recordLoginInfo(LoginHelper.getUserId(), username, true, "登录成功");
        log.info("用户登录, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete(RedisPrefix.ONLINE_TOKEN_KEY + tokenValue);
        log.info("用户退出, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete(RedisPrefix.ONLINE_TOKEN_KEY + tokenValue);
        log.info("用户被踢下线, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete(RedisPrefix.ONLINE_TOKEN_KEY + tokenValue);
        log.info("用户被顶下线, userId:{}, token:{}", loginId, tokenValue);
    }
}

