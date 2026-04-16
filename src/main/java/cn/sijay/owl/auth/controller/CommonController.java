package cn.sijay.owl.auth.controller;

import cn.sijay.owl.auth.entity.Route;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.auth.service.CommonService;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CommonController
 *
 * @author sijay
 * @since 2026-04-16
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/common")
@RestController
public class CommonController extends BaseController {
    private final CommonService commonService;

    /**
     * 获取用户信息
     */
    @GetMapping("/userinfo")
    public Result<UserInfo> userinfo() {
        return success(commonService.getUserInfo());
    }

    /**
     * 获取所有路由
     */
    @GetMapping("/getRoutes")
    public Result<List<Route>> getRoutes() {
        return success(commonService.getRoutes());
    }
}
