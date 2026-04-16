package cn.sijay.owl.common.utils;

import lombok.Getter;
import org.redisson.api.*;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * RedisUtil
 * Redis工具类 - 基于Redisson
 *
 * @author sijay
 * @since 2026-04-15
 */
public class RedisUtil {

    @Getter
    private final static RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

    // ==================== String 操作 ====================

    /**
     * 设置值
     */
    public static <T> void set(String key, T value) {
        redissonClient.getBucket(key).set(value);
    }

    /**
     * 设置值并指定过期时间
     */
    public static <T> void set(String key, T value, long second) {
        redissonClient.getBucket(key).set(value, Duration.ofSeconds(second));
    }

    /**
     * 获取值
     */
    public static <T> T get(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 获取值并设置过期时间
     */
    public static <T> T getAndExpire(String key, long second) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.getAndExpire(Duration.ofSeconds(second));
    }

    /**
     * 判断key是否存在
     */
    public static boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 删除key
     */
    public static boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }

    /**
     * 批量删除
     */
    public static long delete(Collection<String> keys) {
        return redissonClient.getKeys().delete(keys.toArray(new String[0]));
    }

    /**
     * 设置过期时间
     */
    public static boolean expire(String key, long second) {
        return redissonClient.getBucket(key).expire(Duration.ofSeconds(second));
    }

    /**
     * 获取剩余过期时间（毫秒）
     */
    public static long getExpire(String key) {
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    // ==================== Hash 操作 ====================

    /**
     * 设置Hash字段
     */
    public static <T> void hSet(String key, String field, T value) {
        redissonClient.getMap(key).put(field, value);
    }

    /**
     * 获取Hash字段
     */
    public static <T> T hGet(String key, String field) {
        RMap<String, T> map = redissonClient.getMap(key);
        return map.get(field);
    }

    /**
     * 批量设置Hash
     */
    public static <T> void hSetAll(String key, Map<String, T> map) {
        redissonClient.getMap(key).putAll(map);
    }

    /**
     * 获取整个Hash
     */
    public static <K, V> Map<K, V> hGetAll(String key) {
        RMap<K, V> map = redissonClient.getMap(key);
        return map.readAllMap();
    }

    /**
     * 删除Hash字段
     */
    public static long hDelete(String key, Object... fields) {
        RMap<Object, Object> map = redissonClient.getMap(key);
        return map.fastRemove(fields);
    }

    /**
     * 判断Hash字段是否存在
     */
    public static boolean hExists(String key, String field) {
        return redissonClient.getMap(key).containsKey(field);
    }

    // ==================== List 操作 ====================

    /**
     * 添加元素到列表尾部
     */
    public static <T> boolean lPush(String key, T value) {
        return redissonClient.getList(key).add(value);
    }

    /**
     * 批量添加
     */
    public static <T> boolean lPushAll(String key, Collection<T> values) {
        return redissonClient.getList(key).addAll(values);
    }

    /**
     * 获取列表指定范围元素
     */
    public static <T> List<T> lRange(String key, int start, int end) {
        RList<T> list = redissonClient.getList(key);
        return list.range(start, end);
    }

    /**
     * 获取列表所有元素
     */
    public static <T> List<T> lGetAll(String key) {
        return redissonClient.<T>getList(key).readAll();
    }

    /**
     * 获取列表长度
     */
    public static int lSize(String key) {
        return redissonClient.getList(key).size();
    }

    /**
     * 移除并返回列表头部元素
     */
    public static <T> T lPop(String key) {
        RList<T> list = redissonClient.getList(key);
        return list.isEmpty() ? null : list.removeFirst();
    }

    // ==================== Set 操作 ====================

    /**
     * 添加元素到集合
     */
    public static <T> boolean sAdd(String key, T value) {
        return redissonClient.getSet(key).add(value);
    }

    /**
     * 判断元素是否在集合中
     */
    public static <T> boolean sContains(String key, T value) {
        return redissonClient.getSet(key).contains(value);
    }

    /**
     * 获取集合所有元素
     */
    public static <T> Set<T> sMembers(String key) {
        RSet<T> set = redissonClient.getSet(key);
        return set.readAll();
    }

    /**
     * 获取集合大小
     */
    public static int sSize(String key) {
        return redissonClient.getSet(key).size();
    }

    /**
     * 移除集合元素
     */
    public static <T> boolean sRemove(String key, T value) {
        return redissonClient.getSet(key).remove(value);
    }

    // ==================== ZSet 操作 ====================

    /**
     * 添加元素到有序集合
     */
    public static <T> boolean zAdd(String key, T value, double score) {
        return redissonClient.getScoredSortedSet(key).add(score, value);
    }

    /**
     * 获取有序集合指定分数范围的元素
     */
    public static <V> Collection<V> zRangeByScore(String key, double min, double max) {
        RScoredSortedSet<V> zset = redissonClient.getScoredSortedSet(key);
        return zset.valueRange(min, true, max, true);
    }

    /**
     * 获取有序集合大小
     */
    public static int zSize(String key) {
        return redissonClient.getScoredSortedSet(key).size();
    }

    /**
     * 移除有序集合元素
     */
    public static <T> boolean zRemove(String key, T value) {
        return redissonClient.getScoredSortedSet(key).remove(value);
    }

    // ==================== 分布式锁 ====================

    /**
     * 获取分布式锁
     */
    public static RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 尝试获取锁（非阻塞）
     */
    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return redissonClient.getLock(lockKey).tryLock(waitTime, leaseTime, unit);
    }

    /**
     * 释放锁
     */
    public static void unlock(RLock lock) {
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    // ==================== 原子操作 ====================

    /**
     * 原子递增
     */
    public static long incr(String key, long delta) {
        return redissonClient.getAtomicLong(key).addAndGet(delta);
    }

    /**
     * 原子递减
     */
    public static long decr(String key, long delta) {
        return redissonClient.getAtomicLong(key).addAndGet(-delta);
    }

    // ==================== 消息 ====================

    /**
     * 发布通道消息
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     * @param consumer   自定义处理
     */
    public static <T> void publish(String channelKey, T msg, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
        consumer.accept(msg);
    }

    /**
     * 发布消息到指定的频道
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     */
    public static <T> void publish(String channelKey, T msg) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
    }

    /**
     * 订阅通道接收消息
     *
     * @param channelKey 通道key
     * @param clazz      消息类型
     * @param consumer   自定义处理
     */
    public static <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }
}


