package com.vulcan.cache.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统一缓存服务接口，定义缓存操作的方法
 *
 * @author Y
 */
public interface CacheService {
    
    /**
     * 设置缓存
     *
     * @param key 缓存键
     * @param value 缓存值
     */
    void set(String key, Object value);
    
    /**
     * 设置缓存并设置过期时间
     *
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 过期时间
     */
    void set(String key, Object value, Duration duration);
    
    /**
     * 获取缓存
     *
     * @param key 缓存键
     * @param clazz 返回类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);
    
    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    void delete(String key);
    
    /**
     * 批量删除缓存
     *
     * @param keys 缓存键集合
     */
    void delete(List<String> keys);
    
    /**
     * 根据模式删除缓存
     *
     * @param pattern 模式
     */
    void deleteByPattern(String pattern);
    
    /**
     * 设置过期时间
     *
     * @param key 缓存键
     * @param duration 过期时间
     */
    void expire(String key, Duration duration);
    
    /**
     * 判断键是否存在
     *
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);
    
    /**
     * 递增
     *
     * @param key 缓存键
     * @param delta 递增因子
     * @return 递增后的值
     */
    Long increment(String key, long delta);
    
    /**
     * 递减
     *
     * @param key 缓存键
     * @param delta 递减因子
     * @return 递减后的值
     */
    Long decrement(String key, long delta);
    
    /**
     * 获取哈希表中的值
     *
     * @param key 缓存键
     * @param hashKey 哈希键
     * @param clazz 返回类型
     * @return 哈希值
     */
    <T> T getHash(String key, String hashKey, Class<T> clazz);
    
    /**
     * 设置哈希表中的值
     *
     * @param key 缓存键
     * @param hashKey 哈希键
     * @param value 哈希值
     */
    void setHash(String key, String hashKey, Object value);
    
    /**
     * 设置哈希表中的值并设置过期时间
     *
     * @param key 缓存键
     * @param hashKey 哈希键
     * @param value 哈希值
     * @param duration 过期时间
     */
    void setHash(String key, String hashKey, Object value, Duration duration);
    
    /**
     * 获取所有哈希表数据
     *
     * @param key 缓存键
     * @return 哈希表数据
     */
    Map<Object, Object> getAllHash(String key);
} 