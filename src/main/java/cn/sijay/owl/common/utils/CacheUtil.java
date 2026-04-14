package cn.sijay.owl.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * CacheUtil
 * 提供默认缓存实例、常用缓存操作以及自定义缓存创建方法
 *
 * @author sijay
 * @since 2026-04-14
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtil {

    // 默认缓存实例（最大容量10000，写入后60分钟过期）
    private static final Cache<String, Object> DEFAULT_CACHE = Caffeine.newBuilder()
                                                                       .maximumSize(10000)
                                                                       .expireAfterWrite(60, TimeUnit.MINUTES)
                                                                       .recordStats()          // 开启统计，可选
                                                                       .build();

    // ==================== 默认缓存操作 ====================

    /**
     * 存入默认缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        DEFAULT_CACHE.put(key, value);
    }

    /**
     * 从默认缓存获取值，若不存在则通过 mappingFunction 加载并存入缓存
     *
     * @param key             键
     * @param mappingFunction 加载函数
     * @param <V>             值类型
     * @return 存在的值或加载后的值
     */
    @SuppressWarnings("unchecked")
    public static <V> V get(String key, Function<String, ? extends V> mappingFunction) {
        return (V) DEFAULT_CACHE.get(key, mappingFunction);
    }

    /**
     * 从默认缓存获取值，若不存在则返回 null
     *
     * @param key 键
     * @param <V> 值类型
     * @return 存在的值或 null
     */
    @SuppressWarnings("unchecked")
    public static <V> V getIfPresent(String key) {
        return (V) DEFAULT_CACHE.getIfPresent(key);
    }

    /**
     * 从默认缓存中移除指定键
     *
     * @param key 键
     */
    public static void delete(String key) {
        DEFAULT_CACHE.invalidate(key);
    }

    /**
     * 清空默认缓存
     */
    public static void clear() {
        DEFAULT_CACHE.invalidateAll();
    }

    /**
     * 获取默认缓存的当前大小（近似值）
     *
     * @return 缓存中键值对数量
     */
    public static long size() {
        return DEFAULT_CACHE.estimatedSize();
    }

    // ==================== 自定义缓存创建 ====================

    /**
     * 创建一个手动管理的 Cache 实例
     *
     * @param builder 已配置好的 Caffeine 构建器
     * @param <V>     值类型
     * @return Cache 实例
     */
    public static <V> Cache<String, V> newCache(Caffeine<String, V> builder) {
        return builder.build();
    }

    /**
     * 创建一个自动加载的 LoadingCache 实例
     *
     * @param builder 已配置好的 Caffeine 构建器
     * @param loader  加载函数（当键不存在时调用）
     * @param <V>     值类型
     * @return LoadingCache 实例
     */
    public static <V> LoadingCache<String, V> newLoadingCache(Caffeine<String, V> builder,
                                                              Function<String, V> loader) {
        return builder.build(loader::apply);
    }

    /**
     * 快速创建基于最大容量的手动缓存
     *
     * @param maxSize       最大容量
     * @param expireMinutes 过期时间（分钟）
     * @param <V>           值类型
     * @return Cache 实例
     */
    public static <V> Cache<String, V> createSimpleCache(long maxSize, long expireMinutes) {
        return Caffeine.newBuilder()
                       .maximumSize(maxSize)
                       .expireAfterWrite(expireMinutes, TimeUnit.MINUTES)
                       .build();
    }

    // ==================== 默认缓存统计信息（可选） ====================

    /**
     * 获取默认缓存的统计信息
     *
     * @return 统计字符串
     */
    public static String getStats() {
        return DEFAULT_CACHE.stats().toString();
    }
}
