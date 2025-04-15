package com.vulcan.code.service;

import com.vulcan.domain.entity.dto.CodeRuleDto;
import com.vulcan.domain.entity.param.CodeRuleParam;

import java.util.List;

/**
 * 编码规则服务接口
 *
 * @author Y
 */
public interface CodeRuleService {

    /**
     * 生成编码
     *
     * @param param 参数
     * @return 生成的编码
     */
    String generateCode(CodeRuleParam param);

    /**
     * 批量生成编码
     *
     * @param param 参数
     * @param count 数量
     * @return 生成的编码列表
     */
    List<String> batchGenerateCode(CodeRuleParam param, Integer count);

    /**
     * 添加编码规则
     *
     * @param codeRuleDto 编码规则
     * @return 添加结果
     */
    boolean addCodeRule(CodeRuleDto codeRuleDto);

    /**
     * 修改编码规则
     *
     * @param codeRuleDto 编码规则
     * @return 修改结果
     */
    boolean updateCodeRule(CodeRuleDto codeRuleDto);

    /**
     * 删除编码规则
     *
     * @param id 编码规则ID
     * @return 删除结果
     */
    boolean deleteCodeRule(Long id);

    /**
     * 获取编码规则
     *
     * @param id 编码规则ID
     * @return 编码规则
     */
    CodeRuleDto getCodeRule(Long id);

    /**
     * 获取编码规则
     *
     * @param ruleCode 规则编码
     * @return 编码规则
     */
    CodeRuleDto getCodeRuleByCode(String ruleCode);

    /**
     * 获取编码规则列表
     *
     * @param codeRuleDto 查询条件
     * @return 编码规则列表
     */
    List<CodeRuleDto> listCodeRules(CodeRuleDto codeRuleDto);

    /**
     * 重置编码规则序列
     *
     * @param id 编码规则ID
     * @param sequence 序列值
     * @return 重置结果
     */
    boolean resetSequence(Long id, Integer sequence);

    /**
     * 启用编码规则
     *
     * @param id 编码规则ID
     * @return 启用结果
     */
    boolean enableCodeRule(Long id);

    /**
     * 禁用编码规则
     *
     * @param id 编码规则ID
     * @return 禁用结果
     */
    boolean disableCodeRule(Long id);
} 