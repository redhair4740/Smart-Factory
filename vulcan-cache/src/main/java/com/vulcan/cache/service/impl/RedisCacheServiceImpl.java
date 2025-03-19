package com.vulcan.cache.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vulcan.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存服务实现类
 *
 * @author Y
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("设置缓存异常：key={}", key, e);
        }
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        try {
            redisTemplate.opsForValue().set(key, value, duration);
        } catch (Exception e) {
            log.error("设置缓存异常：key={}, duration={}", key, duration, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
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
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("删除缓存异常：key={}", key, e);
        }
    }

    @Override
    public void delete(List<String> keys) {
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("批量删除缓存异常：keys={}", keys, e);
        }
    }

    @Override
    public void deleteByPattern(String pattern) {
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            log.error("根据模式删除缓存异常：pattern={}", pattern, e);
        }
    }

    @Override
    public void expire(String key, Duration duration) {
        try {
            redisTemplate.expire(key, duration.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("设置缓存过期时间异常：key={}, duration={}", key, duration, e);
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("判断缓存是否存在异常：key={}", key, e);
            return false;
        }
    }

    @Override
    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("缓存递增异常：key={}, delta={}", key, delta, e);
            return null;
        }
    }

    @Override
    public Long decrement(String key, long delta) {
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            log.error("缓存递减异常：key={}, delta={}", key, delta, e);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getHash(String key, String hashKey, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForHash().get(key, hashKey);
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
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception e) {
            log.error("设置哈希缓存异常：key={}, hashKey={}", key, hashKey, e);
        }
    }

    @Override
    public void setHash(String key, String hashKey, Object value, Duration duration) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            redisTemplate.expire(key, duration);
        } catch (Exception e) {
            log.error("设置哈希缓存异常：key={}, hashKey={}, duration={}", key, hashKey, duration, e);
        }
    }

    @Override
    public Map<Object, Object> getAllHash(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("获取所有哈希缓存异常：key={}", key, e);
            return Collections.emptyMap();
        }
    }
} 