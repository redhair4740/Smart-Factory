package com.vulcan.cache.service.impl;

import com.vulcan.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 组合缓存服务实现类，同时使用Redis和Caffeine本地缓存
 * 读取时优先从本地缓存获取，本地缓存没有再从Redis获取
 * 写入时同时写入本地缓存和Redis
 *
 * @author Y
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CompositeCacheServiceImpl implements CacheService {

    private final RedisCacheServiceImpl redisCacheService;
    private final CaffeineCacheServiceImpl caffeineCacheService;

    @Override
    public void set(String key, Object value) {
        caffeineCacheService.set(key, value);
        redisCacheService.set(key, value);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        caffeineCacheService.set(key, value, duration);
        redisCacheService.set(key, value, duration);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        // 优先从本地缓存获取
        T result = caffeineCacheService.get(key, clazz);
        
        if (result == null) {
            // 本地缓存没有，从Redis获取
            result = redisCacheService.get(key, clazz);
            
            // 如果Redis中有数据，回填到本地缓存
            if (result != null) {
                caffeineCacheService.set(key, result);
            }
        }
        
        return result;
    }

    @Override
    public void delete(String key) {
        caffeineCacheService.delete(key);
        redisCacheService.delete(key);
    }

    @Override
    public void delete(List<String> keys) {
        caffeineCacheService.delete(keys);
        redisCacheService.delete(keys);
    }

    @Override
    public void deleteByPattern(String pattern) {
        caffeineCacheService.deleteByPattern(pattern);
        redisCacheService.deleteByPattern(pattern);
    }

    @Override
    public void expire(String key, Duration duration) {
        // 本地缓存不支持设置过期时间，只在Redis中设置
        redisCacheService.expire(key, duration);
    }

    @Override
    public boolean exists(String key) {
        // 优先检查本地缓存
        if (caffeineCacheService.exists(key)) {
            return true;
        }
        
        // 本地缓存不存在，检查Redis
        return redisCacheService.exists(key);
    }

    @Override
    public Long increment(String key, long delta) {
        // 递增操作需要保证一致性，仅在Redis中执行
        Long result = redisCacheService.increment(key, delta);
        
        // 更新本地缓存
        if (result != null) {
            caffeineCacheService.set(key, result);
        }
        
        return result;
    }

    @Override
    public Long decrement(String key, long delta) {
        // 递减操作需要保证一致性，仅在Redis中执行
        Long result = redisCacheService.decrement(key, delta);
        
        // 更新本地缓存
        if (result != null) {
            caffeineCacheService.set(key, result);
        }
        
        return result;
    }

    @Override
    public <T> T getHash(String key, String hashKey, Class<T> clazz) {
        // 优先从本地缓存获取
        T result = caffeineCacheService.getHash(key, hashKey, clazz);
        
        if (result == null) {
            // 本地缓存没有，从Redis获取
            result = redisCacheService.getHash(key, hashKey, clazz);
            
            // 如果Redis中有数据，回填到本地缓存
            if (result != null) {
                caffeineCacheService.setHash(key, hashKey, result);
            }
        }
        
        return result;
    }

    @Override
    public void setHash(String key, String hashKey, Object value) {
        caffeineCacheService.setHash(key, hashKey, value);
        redisCacheService.setHash(key, hashKey, value);
    }

    @Override
    public void setHash(String key, String hashKey, Object value, Duration duration) {
        caffeineCacheService.setHash(key, hashKey, value);
        redisCacheService.setHash(key, hashKey, value, duration);
    }

    @Override
    public Map<Object, Object> getAllHash(String key) {
        // 优先从本地缓存获取
        Map<Object, Object> result = caffeineCacheService.getAllHash(key);
        
        if (result.isEmpty()) {
            // 本地缓存没有，从Redis获取
            result = redisCacheService.getAllHash(key);
            
            // 如果Redis中有数据，回填到本地缓存
            if (!result.isEmpty()) {
                result.forEach((hashKey, value) -> caffeineCacheService.setHash(key, hashKey.toString(), value));
            }
        }
        
        return result;
    }
} 