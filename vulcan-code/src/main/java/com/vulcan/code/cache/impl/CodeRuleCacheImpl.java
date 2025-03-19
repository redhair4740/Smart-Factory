package com.vulcan.code.cache.impl;

import com.vulcan.cache.service.CacheService;
import com.vulcan.code.cache.CodeRuleCache;
import com.vulcan.domain.entity.po.CodeRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

/**
 * 编码规则缓存实现
 *
 * @author Y
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CodeRuleCacheImpl implements CodeRuleCache {

    private final CacheService cacheService;
    
    /**
     * 缓存过期时间（12小时）
     */
    private static final Duration CACHE_EXPIRE = Duration.ofHours(12);

    @Override
    public Optional<CodeRule> getCodeRule(String ruleCode) {
        try {
            String cacheKey = getCacheKey(ruleCode);
            CodeRule codeRule = cacheService.get(cacheKey, CodeRule.class);
            return Optional.ofNullable(codeRule);
        } catch (Exception e) {
            log.error("获取编码规则缓存失败: {}", ruleCode, e);
            return Optional.empty();
        }
    }

    @Override
    public void cacheCodeRule(CodeRule codeRule) {
        if (codeRule == null || codeRule.getRuleCode() == null) {
            return;
        }
        try {
            String cacheKey = getCacheKey(codeRule.getRuleCode());
            cacheService.set(cacheKey, codeRule, CACHE_EXPIRE);
        } catch (Exception e) {
            log.error("缓存编码规则失败: {}", codeRule.getRuleCode(), e);
        }
    }

    @Override
    public void deleteCodeRule(String ruleCode) {
        if (ruleCode == null) {
            return;
        }
        try {
            String cacheKey = getCacheKey(ruleCode);
            cacheService.delete(cacheKey);
        } catch (Exception e) {
            log.error("删除编码规则缓存失败: {}", ruleCode, e);
        }
    }

    @Override
    public void clearAll() {
        try {
            cacheService.deleteByPattern(CACHE_PREFIX + "*");
        } catch (Exception e) {
            log.error("清空编码规则缓存失败", e);
        }
    }
    
    /**
     * 获取缓存Key
     *
     * @param ruleCode 规则编码
     * @return 缓存Key
     */
    private String getCacheKey(String ruleCode) {
        return CACHE_PREFIX + ruleCode;
    }
} 