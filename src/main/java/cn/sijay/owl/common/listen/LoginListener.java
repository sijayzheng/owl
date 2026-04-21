package cn.sijay.owl.common.listen;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.sijay.owl.auth.service.LoginService;
import cn.sijay.owl.common.utils.LoginHelper;
import cn.sijay.owl.common.utils.RedisUtil;
import cn.sijay.owl.common.utils.SpringUtil;
import cn.sijay.owl.log.entity.LogLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginParameter loginModel) {
//        OnlineUser dto = new OnlineUser();
//        dto.setLoginTime(System.currentTimeMillis());
//        dto.setTokenId(tokenValue);
        String username = LoginHelper.getUsername();
//        dto.setUserName(username);
//        dto.setDeptName(LoginHelper.getDeptName());
//        if (tokenConfig.getTimeout() == -1) {
//            RedisUtil.set("ONLINE_TOKEN_KEY:" + tokenValue, dto);
//        } else {
//            RedisUtil.set("ONLINE_TOKEN_KEY:" + tokenValue, dto, tokenConfig.getTimeout());
//        }
        // 记录登录日志
        LogLogin logLogin = new LogLogin();
        logLogin.setUsername(username);
        logLogin.setSucceeded(true);
        logLogin.setMessage("登录成功");
        SpringUtil.context().publishEvent(logLogin);
        // 更新登录信息
        loginService.recordLoginInfo(LoginHelper.getUserId(), username, true, "登录成功");
        log.info("用户登录, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete("ONLINE_TOKEN_KEY:" + tokenValue);
        log.info("用户退出, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete("ONLINE_TOKEN_KEY:" + tokenValue);
        log.info("用户被踢下线, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        RedisUtil.delete("ONLINE_TOKEN_KEY:" + tokenValue);
        log.info("用户被顶下线, userId:{}, token:{}", loginId, tokenValue);
    }
}

