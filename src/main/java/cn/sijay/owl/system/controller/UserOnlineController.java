package cn.sijay.owl.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.constants.RedisPrefix;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.RedisUtil;
import cn.sijay.owl.common.utils.StreamUtil;
import cn.sijay.owl.system.entity.OnlineUser;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线用户监控
 *
 * @author sijay
 * @since 2026-05-07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/online")
public class UserOnlineController extends BaseController {

    /**
     * 获取在线用户监控列表
     *
     * @param username 用户名
     */
    @SaCheckPermission("monitor:online:list")
    @GetMapping("/list")
    public Result<List<OnlineUser>> list(String username) {
        // 获取所有未过期的 token
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        List<OnlineUser> onlineUserList = new ArrayList<>();
        for (String key : keys) {
            String token = StringUtils.substringAfterLast(key, ":");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                continue;
            }
            onlineUserList.add(RedisUtil.get(RedisPrefix.ONLINE_TOKEN_KEY + token));
        }
        if (StringUtil.isNotEmpty(username)) {
            onlineUserList = StreamUtil.filter(onlineUserList, userOnline ->
                StringUtil.equals(username, userOnline.username())
            );
        }
        Collections.reverse(onlineUserList);
        onlineUserList.removeAll(Collections.singleton(null));
        return success(onlineUserList);
    }

    /**
     * 强退用户
     *
     * @param tokenId token值
     */
    @SaCheckPermission("monitor:online:forceLogout")
    @AccessLog(title = "在线用户", operateType = OperateType.FORCE)
    @DeleteMapping("/{tokenId}")
    public Result<String> forceLogout(@PathVariable String tokenId) {
        try {
            StpUtil.kickoutByTokenValue(tokenId);
        } catch (NotLoginException ignored) {
        }
        return success();
    }

    /**
     * 获取当前用户登录在线设备
     */
    @GetMapping()
    public Result<List<OnlineUser>> getInfo() {
        // 获取指定账号 id 的 token 集合
        List<OnlineUser> onlineUserList = StpUtil.getTokenValueListByLoginId(StpUtil.getLoginIdAsString())
                                                 .stream()
                                                 .filter(token -> StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) >= -1)
                                                 .map(token -> (OnlineUser) RedisUtil.get(RedisPrefix.ONLINE_TOKEN_KEY + token))
                                                 .collect(Collectors.toList());
        //复制和处理 OnlineUser 对象列表
        Collections.reverse(onlineUserList);
        onlineUserList.removeAll(Collections.singleton(null));
        return success(onlineUserList);
    }

    /**
     * 强退当前在线设备
     *
     * @param tokenId token值
     */
    @AccessLog(title = "在线设备", operateType = OperateType.FORCE)
    @PostMapping("/{tokenId}")
    public Result<String> remove(@PathVariable("tokenId") String tokenId) {
        try {
            // 获取指定账号 id 的 token 集合
            StpUtil.getTokenValueListByLoginId(StpUtil.getLoginIdAsString())
                   .stream()
                   .filter(key -> key.equals(tokenId))
                   .findFirst()
                   .ifPresent(key -> StpUtil.kickoutByTokenValue(tokenId));
        } catch (NotLoginException ignored) {
        }
        return success();
    }

}
