package cn.sijay.owl.common.service;

import cn.sijay.owl.common.exceptions.BaseException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.service.Config;
import org.lionsoul.ip2region.service.InvalidConfigException;
import org.lionsoul.ip2region.service.Ip2Region;
import org.lionsoul.ip2region.xdb.XdbException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Ip2RegionService
 *
 * @author sijay
 * @since 2026-04-16
 */
@Slf4j
@Component
public class Ip2RegionService {
    // Ip2Region服务实例
    private static Ip2Region ip2Region;

    @PostConstruct
    public void init() {
        try {
            // 1, 创建 v4 的配置：指定缓存策略和 v4 的 xdb 文件路径
            final Config v4Config = Config.custom()
                                          .setCachePolicy(Config.VIndexCache)     // 指定缓存策略:  NoCache / VIndexCache / BufferCache
                                          .setSearchers(15)                       // 设置初始化的查询器数量
                                          // .setCacheSliceBytes(int)             // 设置缓存的分片字节数，默认为 50MiB
                                          // .setXdbInputStream(InputStream)      // 设置 v4 xdb 文件的 inputstream 对象
                                          // .setXdbFile(File)                    // 设置 v4 xdb File 对象
                                          // .setFairLock(boolean)                // 设置 ReentrantLock 是否使用公平锁
                                          .setXdbPath("ip2region_v4.xdb")    // 设置 v4 xdb 文件的路径
                                          .asV4();    // 指定为 v4 配置
            // 2, 创建 v6 的配置：指定缓存策略和 v6 的 xdb 文件路径
            final Config v6Config = Config.custom()
                                          .setCachePolicy(Config.VIndexCache)     // 指定缓存策略: NoCache / VIndexCache / BufferCache
                                          .setSearchers(15)                       // 设置初始化的查询器数量
                                          // .setCacheSliceBytes(int)             // 设置缓存的分片字节数，默认为 50MiB
                                          // .setXdbInputStream(InputStream)      // 设置 v6 xdb 文件的 inputstream 对象
                                          // .setXdbFile(File)                    // 设置 v6 xdb File 对象
                                          // .setFairLock(boolean)                // 设置 ReentrantLock 是否使用公平锁
                                          .setXdbPath("ip2region_v6.xdb")    // 设置 v6 xdb 文件的路径
                                          .asV6();    // 指定为 v6 配置
            // 备注：Xdb 三种初始化输入的优先级：XdbInputStream -> XdbFile -> XdbPath
            // setXdbInputStream 仅方便使用者从 jar 包中加载 xdb 文件内容，这时 cachePolicy 只能设置为 Config.BufferCache
            // 3，通过上述配置创建 Ip2Region 查询服务
            ip2Region = Ip2Region.create(v4Config, v6Config);
            log.info("ip2region 服务初始化完成");
        } catch (IOException | InvalidConfigException | XdbException e) {
            log.error("ip2region 服务初始化失败", e);
            throw new BaseException("ip2region 初始化失败", e);
        }
    }

    public String getIpLocation(String ip) {
        try {
            // 4，导出 ip2region 服务作为全局变量，进行双版本的IP地址的并发查询，例如：
            return ip2Region.search(ip);
        } catch (Exception e) {
            log.error("IP地址查询异常, ip={}", ip, e);
            return null;
        }
    }

    @PreDestroy
    public void destroy() {
        if (ip2Region != null) {
            try {
                // 5，在服务需要关闭的时候，同时关闭 ip2region 查询服务
                // 备注：close 方法只需要在整个服务关闭的时候关闭，查询途中不需要操作
                ip2Region.close();
                log.info("ip2region 资源已释放");
            } catch (InterruptedException e) {
                log.error("ip2region 资源释放失败", e);
            }
        }
    }
}
