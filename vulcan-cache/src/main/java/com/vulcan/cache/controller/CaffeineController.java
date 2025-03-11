package com.vulcan.cache.controller;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.cache.controller
 * @name: CaffeineController
 * @Date: 2024/4/16 下午7:58
 * @Description Caffeine缓存控制器，提供缓存数据的添加和获取接口
 */
@RestController
@RequestMapping("/cache")
public class CaffeineController {

    @Resource
    private Cache<String, Object> caffeineCache;

    /**
     * 添加缓存数据
     */
    @GetMapping("/setCache")
    public void setCache()
    {
        // 添加缓存数据
        caffeineCache.put("key", "value");
    }

    /**
     * 添加缓存数据
     */
    @GetMapping("/getCache")
    public String getCache()
    {
        // 添加缓存数据
        if(caffeineCache.getIfPresent("key") != null){
            return Objects.requireNonNull(caffeineCache.getIfPresent("key")).toString();
        }
        return null;
    }

}