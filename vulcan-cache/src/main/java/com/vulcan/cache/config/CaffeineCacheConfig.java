package com.vulcan.cache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.cache.config
 * @name: CaffeineCacheConfig
 * @Date: 2024/4/16 下午7:58
 * @Description Caffeine缓存配置类
 */
@Configuration
public class CaffeineCacheConfig {
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }
}