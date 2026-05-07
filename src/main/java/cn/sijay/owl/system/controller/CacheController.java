package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.system.entity.CacheInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 缓存监控
 *
 * @author sijay
 * @since 2026-05-07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/cache")
public class CacheController extends BaseController {

    private final RedissonConnectionFactory connectionFactory;

    /**
     * 获取缓存监控列表
     */
    @SaCheckPermission("monitor:cache:list")
    @GetMapping()
    public Result<CacheInfo> getInfo() {
        RedisConnection connection = connectionFactory.getConnection();
        Properties commandStats = connection.commands().info("commandstats");

        List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", Strings.CI.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        CacheInfo infoVo = new CacheInfo(
            connection.commands().info(),
            connection.commands().dbSize(),
            pieList
        );
        return success(infoVo);
    }

}
