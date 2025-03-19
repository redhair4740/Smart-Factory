package com.vulcan.code.cache;

import com.vulcan.domain.entity.po.CodeRule;

import java.util.Optional;

/**
 * 编码规则缓存接口
 *
 * @author Y
 */
public interface CodeRuleCache {

    /**
     * 缓存前缀
     */
    String CACHE_PREFIX = "code_rule:";

    /**
     * 根据规则编码获取缓存的编码规则
     *
     * @param ruleCode 规则编码
     * @return 编码规则
     */
    Optional<CodeRule> getCodeRule(String ruleCode);

    /**
     * 缓存编码规则
     *
     * @param codeRule 编码规则
     */
    void cacheCodeRule(CodeRule codeRule);

    /**
     * 删除编码规则缓存
     *
     * @param ruleCode 规则编码
     */
    void deleteCodeRule(String ruleCode);

    /**
     * 清空所有编码规则缓存
     */
    void clearAll();
} 