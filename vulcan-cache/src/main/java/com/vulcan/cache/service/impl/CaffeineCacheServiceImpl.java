package com.vulcan.cache.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.vulcan.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Caffeine本地缓存服务实现类
 *
 * @author Y
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaffeineCacheServiceImpl implements CacheService {

    private final Cache<String, Object> caffeineCache;
    private final ObjectMapper objectMapper;
    
    // 使用ConcurrentHashMap来存储哈希结构
    private final ConcurrentHashMap<String, Map<String, Object>> hashCache = new ConcurrentHashMap<>();

    @Override
    public void set(String key, Object value) {
        try {
            caffeineCache.put(key, value);
        } catch (Exception e) {
            log.error("设置缓存异常：key={}", key, e);
        }
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        try {
            // Caffeine不支持直接设置单个key的过期时间，这里简单实现
            caffeineCache.put(key, value);
        } catch (Exception e) {
            log.error("设置缓存异常：key={}, duration={}", key, duration, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = caffeineCache.getIfPresent(key);
            if (value == null) {
                return null;
            }
            
            if (clazz.isInstance(value)) {
                return (T) value;
            }
            
            // 处理JSON字符串
            if (value instanceof String) {
                return objectMapper.readValue((String) value, clazz);
            }
            
            // 对于复杂对象，通过JSON转换
            String jsonValue = objectMapper.writeValueAsString(value);
            return objectMapper.readValue(jsonValue, clazz);
        } catch (JsonProcessingException e) {
            log.error("获取缓存转换JSON异常：key={}, clazz={}", key, clazz.getName(), e);
            return null;
        } catch (Exception e) {
            log.error("获取缓存异常：key={}, clazz={}", key, clazz.getName(), e);
            return null;
        }
    }

    @Override
    public void delete(String key) {
        try {
            caffeineCache.invalidate(key);
            hashCache.remove(key);
        } catch (Exception e) {
            log.error("删除缓存异常：key={}", key, e);
        }
    }

    @Override
    public void delete(List<String> keys) {
        try {
            caffeineCache.invalidateAll(keys);
            keys.forEach(hashCache::remove);
        } catch (Exception e) {
            log.error("批量删除缓存异常：keys={}", keys, e);
        }
    }

    @Override
    public void deleteByPattern(String pattern) {
        try {
            // Caffeine不支持模式匹配删除，这里简单实现
            Set<String> matchedKeys = caffeineCache.asMap().keySet().stream()
                    .filter(key -> key.matches(pattern.replace("*", ".*")))
                    .collect(Collectors.toSet());
            
            caffeineCache.invalidateAll(matchedKeys);
            
            // 同步删除哈希缓存
            Set<String> matchedHashKeys = hashCache.keySet().stream()
                    .filter(key -> key.matches(pattern.replace("*", ".*")))
                    .collect(Collectors.toSet());
            
            matchedHashKeys.forEach(hashCache::remove);
        } catch (Exception e) {
            log.error("根据模式删除缓存异常：pattern={}", pattern, e);
        }
    }

    @Override
    public void expire(String key, Duration duration) {
        // Caffeine不支持设置单个key的过期时间，这个方法在本地缓存中无效
        log.warn("Caffeine不支持设置单个key的过期时间，忽略expire操作：key={}, duration={}", key, duration);
    }

    @Override
    public boolean exists(String key) {
        try {
            return caffeineCache.getIfPresent(key) != null || hashCache.containsKey(key);
        } catch (Exception e) {
            log.error("判断缓存是否存在异常：key={}", key, e);
            return false;
        }
    }

    @Override
    public Long increment(String key, long delta) {
        try {
            Object value = caffeineCache.getIfPresent(key);
            Long result;
            
            if (value == null) {
                result = delta;
            } else if (value instanceof Number) {
                result = ((Number) value).longValue() + delta;
            } else {
                try {
                    result = Long.parseLong(value.toString()) + delta;
                } catch (NumberFormatException e) {
                    result = delta;
                }
            }
            
            caffeineCache.put(key, result);
            return result;
        } catch (Exception e) {
            log.error("缓存递增异常：key={}, delta={}", key, delta, e);
            return null;
        }
    }

    @Override
    public Long decrement(String key, long delta) {
        return increment(key, -delta);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getHash(String key, String hashKey, Class<T> clazz) {
        try {
            Map<String, Object> hash = hashCache.get(key);
            if (hash == null) {
                return null;
            }
            
            Object value = hash.get(hashKey);
            if (value == null) {
                return null;
            }
            
            if (clazz.isInstance(value)) {
                return (T) value;
            }
            
            // 处理JSON字符串
            if (value instanceof String) {
                return objectMapper.readValue((String) value, clazz);
            }
            
            // 对于复杂对象，通过JSON转换
            String jsonValue = objectMapper.writeValueAsString(value);
            return objectMapper.readValue(jsonValue, clazz);
        } catch (JsonProcessingException e) {
            log.error("获取哈希缓存转换JSON异常：key={}, hashKey={}, clazz={}", key, hashKey, clazz.getName(), e);
            return null;
        } catch (Exception e) {
            log.error("获取哈希缓存异常：key={}, hashKey={}, clazz={}", key, hashKey, clazz.getName(), e);
            return null;
        }
    }

    @Override
    public void setHash(String key, String hashKey, Object value) {
        try {
            Map<String, Object> hash = hashCache.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
            hash.put(hashKey, value);
        } catch (Exception e) {
            log.error("设置哈希缓存异常：key={}, hashKey={}", key, hashKey, e);
        }
    }

    @Override
    public void setHash(String key, String hashKey, Object value, Duration duration) {
        // Caffeine不支持设置过期时间，直接调用不带过期时间的方法
        setHash(key, hashKey, value);
    }

    @Override
    public Map<Object, Object> getAllHash(String key) {
        try {
            Map<String, Object> hash = hashCache.get(key);
            if (hash == null) {
                return Collections.emptyMap();
            }
            
            return Collections.unmodifiableMap(hash);
        } catch (Exception e) {
            log.error("获取所有哈希缓存异常：key={}", key, e);
            return Collections.emptyMap();
        }
    }
} 