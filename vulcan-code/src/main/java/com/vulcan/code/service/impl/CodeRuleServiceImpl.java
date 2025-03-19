package com.vulcan.code.service.impl;

import com.vulcan.code.cache.CodeRuleCache;
import com.vulcan.code.generator.CodeGenerator;
import com.vulcan.domain.entity.dto.CodeRuleDto;
import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.po.CodeRule;
import com.vulcan.domain.repository.CodeQueryRepository;
import com.vulcan.code.service.CodeRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 编码规则服务实现
 *
 * @author Y
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeRuleServiceImpl implements CodeRuleService {

    private final CodeQueryRepository codeRuleRepository;
    private final CodeRuleCache codeRuleCache;
    private final CodeGenerator codeGenerator;
    private final ModelMapper modelMapper;

    @Override
    public String generateCode(CodeRuleParam param) {
        // 参数校验
        if (param == null || param.getRuleCode() == null || param.getRuleCode().isEmpty()) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }

        // 优先从缓存获取编码规则
        CodeRule codeRule = codeRuleCache.getCodeRule(param.getRuleCode())
                .orElseGet(() -> {
                    // 缓存不存在，从数据库获取
                    Optional<CodeRule> optionalCodeRule = codeRuleRepository.findByRuleCode(param.getRuleCode());
                    CodeRule rule = optionalCodeRule.orElseThrow(() ->
                            new IllegalArgumentException("编码规则不存在：" + param.getRuleCode()));
                    
                    // 添加到缓存
                    codeRuleCache.cacheCodeRule(rule);
                    return rule;
                });

        // 检查编码规则是否启用
        if (codeRule.getStatus() != 1) {
            throw new IllegalStateException("编码规则已禁用：" + param.getRuleCode());
        }

        // 生成编码
        return codeGenerator.generate(codeRule, param);
    }

    @Override
    public List<String> batchGenerateCode(CodeRuleParam param, Integer count) {
        if (count == null || count <= 0 || count > 1000) {
            throw new IllegalArgumentException("生成数量必须大于0且不超过1000");
        }

        List<String> codes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            codes.add(generateCode(param));
        }
        return codes;
    }

    @Override
    public boolean addCodeRule(CodeRuleDto codeRuleDto) {
        if (codeRuleDto == null) {
            throw new IllegalArgumentException("编码规则不能为空");
        }
        
        // 检查规则编码是否已存在
        Optional<CodeRule> existingRule = codeRuleRepository.findByRuleCode(codeRuleDto.getRuleCode());
        if (existingRule.isPresent()) {
            throw new IllegalArgumentException("编码规则已存在：" + codeRuleDto.getRuleCode());
        }
        
        // DTO转换为实体
        CodeRule codeRule = modelMapper.map(codeRuleDto, CodeRule.class);
        
        // 设置默认值
        if (codeRule.getCurrentSequence() == null) {
            codeRule.setCurrentSequence(1L);
        }
        if (codeRule.getStep() == null) {
            codeRule.setStep(1);
        }
        if (codeRule.getStatus() == null) {
            codeRule.setStatus(1);
        }
        
        // 保存到数据库
        codeRuleRepository.save(codeRule);
        
        // 更新缓存
        codeRuleCache.cacheCodeRule(codeRule);
        
        return true;
    }

    @Override
    public boolean updateCodeRule(CodeRuleDto codeRuleDto) {
        if (codeRuleDto == null || codeRuleDto.getId() == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 检查规则是否存在
        Optional<CodeRule> optionalCodeRule = codeRuleRepository.findById(codeRuleDto.getId());
        if (!optionalCodeRule.isPresent()) {
            throw new IllegalArgumentException("编码规则不存在：" + codeRuleDto.getId());
        }
        
        CodeRule existingCodeRule = optionalCodeRule.get();
        
        // 更新规则
        modelMapper.map(codeRuleDto, existingCodeRule);
        
        // 保存到数据库
        codeRuleRepository.save(existingCodeRule);
        
        // 更新缓存
        codeRuleCache.cacheCodeRule(existingCodeRule);
        
        return true;
    }

    @Override
    public boolean deleteCodeRule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 检查规则是否存在
        Optional<CodeRule> optionalCodeRule = codeRuleRepository.findById(id);
        if (!optionalCodeRule.isPresent()) {
            throw new IllegalArgumentException("编码规则不存在：" + id);
        }
        
        CodeRule codeRule = optionalCodeRule.get();
        
        // 从数据库删除
        boolean result = codeRuleRepository.deleteById(id);
        
        // 删除缓存
        if (result) {
            codeRuleCache.deleteCodeRule(codeRule.getRuleCode());
        }
        
        return result;
    }

    @Override
    public CodeRuleDto getCodeRule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 从数据库获取
        Optional<CodeRule> optionalCodeRule = codeRuleRepository.findById(id);
        
        return optionalCodeRule
                .map(codeRule -> modelMapper.map(codeRule, CodeRuleDto.class))
                .orElse(null);
    }

    @Override
    public CodeRuleDto getCodeRuleByCode(String ruleCode) {
        if (ruleCode == null || ruleCode.isEmpty()) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
        
        // 优先从缓存获取
        Optional<CodeRule> optionalCodeRule = codeRuleCache.getCodeRule(ruleCode);
        
        if (!optionalCodeRule.isPresent()) {
            // 缓存不存在，从数据库获取
            optionalCodeRule = codeRuleRepository.findByRuleCode(ruleCode);
            
            // 更新缓存
            optionalCodeRule.ifPresent(codeRuleCache::cacheCodeRule);
        }
        
        return optionalCodeRule
                .map(codeRule -> modelMapper.map(codeRule, CodeRuleDto.class))
                .orElse(null);
    }

    @Override
    public List<CodeRuleDto> listCodeRules(CodeRuleDto codeRuleDto) {
        // 转换为实体
        CodeRule condition = modelMapper.map(codeRuleDto, CodeRule.class);
        
        // 查询数据库
        List<CodeRule> codeRules = codeRuleRepository.findByCondition(condition);
        
        // 转换为DTO
        return codeRules.stream()
                .map(codeRule -> modelMapper.map(codeRule, CodeRuleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean resetSequence(Long id, Long sequence) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        if (sequence == null || sequence < 0) {
            throw new IllegalArgumentException("序列值必须大于等于0");
        }
        
        // 检查规则是否存在
        Optional<CodeRule> optionalCodeRule = codeRuleRepository.findById(id);
        if (!optionalCodeRule.isPresent()) {
            throw new IllegalArgumentException("编码规则不存在：" + id);
        }
        
        CodeRule codeRule = optionalCodeRule.get();
        
        // 更新数据库
        boolean result = codeRuleRepository.updateSequence(id, sequence);
        
        // 更新缓存
        if (result) {
            codeRule.setCurrentSequence(sequence);
            codeRuleCache.cacheCodeRule(codeRule);
        }
        
        return result;
    }

    @Override
    public boolean enableCodeRule(Long id) {
        return updateCodeRuleStatus(id, 1);
    }

    @Override
    public boolean disableCodeRule(Long id) {
        return updateCodeRuleStatus(id, 0);
    }
    
    /**
     * 更新编码规则状态
     *
     * @param id 编码规则ID
     * @param status 状态
     * @return 更新结果
     */
    private boolean updateCodeRuleStatus(Long id, Integer status) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 检查规则是否存在
        Optional<CodeRule> optionalCodeRule = codeRuleRepository.findById(id);
        if (!optionalCodeRule.isPresent()) {
            throw new IllegalArgumentException("编码规则不存在：" + id);
        }
        
        CodeRule codeRule = optionalCodeRule.get();
        
        // 更新数据库
        boolean result = codeRuleRepository.updateStatus(id, status);
        
        // 更新缓存
        if (result) {
            codeRule.setStatus(status);
            codeRuleCache.cacheCodeRule(codeRule);
        }
        
        return result;
    }
} 