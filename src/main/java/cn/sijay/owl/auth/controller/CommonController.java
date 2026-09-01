package cn.sijay.owl.auth.controller;

import cn.sijay.owl.auth.entity.Route;
import cn.sijay.owl.auth.entity.UserInfo;
import cn.sijay.owl.auth.enums.EnumType;
import cn.sijay.owl.auth.service.CommonService;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.entity.SelectOption;
import cn.sijay.owl.gen.enums.HtmlType;
import cn.sijay.owl.gen.enums.JavaType;
import cn.sijay.owl.gen.enums.QueryType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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


    @GetMapping("/getEnumSelect/{enumType}")
    public Result<List<SelectOption<String>>> getEnumSelect(@PathVariable("enumType") EnumType enumType) {
        List<SelectOption<String>> list = new ArrayList<>();
        if (enumType == EnumType.JavaType) {
            list = Arrays.stream(JavaType.values()).map(item -> new SelectOption<>(item.getLabel(), item.name())).toList();

        } else if (enumType == EnumType.HtmlType) {
            list = Arrays.stream(HtmlType.values()).map(item -> new SelectOption<>(item.getDescription(), item.name())).toList();

        } else if (enumType == EnumType.QueryType) {
            list = Arrays.stream(QueryType.values()).map(item -> new SelectOption<>(item.getDescription(), item.name())).toList();
        }
        return success(list);
    }
}
