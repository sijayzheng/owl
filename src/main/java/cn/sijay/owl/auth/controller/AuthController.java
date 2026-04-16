package cn.sijay.owl.auth.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.sijay.owl.auth.entity.LoginReq;
import cn.sijay.owl.auth.entity.LoginResp;
import cn.sijay.owl.auth.service.LoginService;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 *
 * @author sijay
 * @since 2026-04-14
 */
@SaIgnore
@Valid
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {
    private final LoginService loginService;

    @AccessLog(title = "授权", operateType = OperateType.LOGIN)
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<LoginResp> login(@Valid @RequestBody LoginReq loginReq) {
        String captchaKey = loginReq.uuid();
        String storedCaptcha = RedisUtil.get(captchaKey);
        if (storedCaptcha == null) {
            return fail("验证码已过期或不存在");
        }
        if (!storedCaptcha.equalsIgnoreCase(loginReq.captcha())) {
            return fail("验证码输入错误");
        }
        RedisUtil.delete(captchaKey);
        return success(loginService.login(loginReq));
    }

    @AccessLog(title = "授权", operateType = OperateType.LOGOUT)
    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Boolean> logout() {
        loginService.logout();
        return result(true, OperateType.LOGOUT);
    }

}
