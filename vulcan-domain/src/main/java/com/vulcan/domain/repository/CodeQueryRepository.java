package com.vulcan.domain.repository;

import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.po.CodeRule;

import java.util.List;
import java.util.Optional;

/**
 * 编码规则仓库接口
 *
 * @author Y
 */
public interface CodeQueryRepository {

    /**
     * 生成编码
     *
     * @param param 参数
     * @return 生成的编码
     */
    String generateCode(CodeRuleParam param);

    /**
     * 保存编码规则
     *
     * @param codeRule 编码规则
     * @return 保存的编码规则
     */
    CodeRule save(CodeRule codeRule);
    
    /**
     * 根据ID查询编码规则
     *
     * @param id 编码规则ID
     * @return 编码规则
     */
    Optional<CodeRule> findById(Long id);
    
    /**
     * 根据规则编码查询编码规则
     *
     * @param ruleCode 规则编码
     * @return 编码规则
     */
    Optional<CodeRule> findByRuleCode(String ruleCode);
    
    /**
     * 查询所有编码规则
     *
     * @return 编码规则列表
     */
    List<CodeRule> findAll();
    
    /**
     * 根据条件查询编码规则
     *
     * @param codeRule 查询条件
     * @return 编码规则列表
     */
    List<CodeRule> findByCondition(CodeRule codeRule);
    
    /**
     * 根据ID删除编码规则
     *
     * @param id 编码规则ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
    
    /**
     * 更新序列值
     *
     * @param id 编码规则ID
     * @param sequence 序列值
     * @return 更新结果
     */
    boolean updateSequence(Long id, Integer sequence);
    
    /**
     * 更新状态
     *
     * @param id 编码规则ID
     * @param status 状态（0-禁用；1-启用）
     * @return 更新结果
     */
    boolean updateStatus(Long id, String status);
} 