package com.vulcan.code.service.impl;

import com.vulcan.code.cache.CodeRuleCache;
import com.vulcan.code.generator.CodeGenerator;
import com.vulcan.domain.entity.dto.CodeRuleDto;
import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.po.CodeRule;
import com.vulcan.domain.repository.CodeQueryRepository;
import com.vulcan.code.service.CodeRuleService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private CodeQueryRepository codeRepository;
    @Resource
    private CodeRuleCache codeRuleCache;
    @Resource
    private CodeGenerator codeGenerator;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public String generateCode(CodeRuleParam param) {
        validateParam(param);

        // 获取编码规则
        CodeRule codeRule = getAndCacheCodeRule(param.getRuleCode());

        // 检查编码规则是否启用
        if (!"q".equals(codeRule.getStatus())) {
            throw new IllegalStateException("编码规则已禁用：" + param.getRuleCode());
        }

        // 生成编码
        return codeGenerator.generate(codeRule, param);
    }

    private void validateParam(CodeRuleParam param) {
        if (param == null || param.getRuleCode() == null || param.getRuleCode().isEmpty()) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
    }

    private CodeRule getAndCacheCodeRule(String ruleCode) {
        // 优先从缓存获取编码规则
        return codeRuleCache.getCodeRule(ruleCode)
                .orElseGet(() -> {
                    // 缓存不存在，从数据库获取
                    Optional<CodeRule> optionalCodeRule = codeRepository.findByRuleCode(ruleCode);
                    CodeRule rule = optionalCodeRule.orElseThrow(() ->
                            new IllegalArgumentException("编码规则不存在：" + ruleCode));
                    
                    // 添加到缓存
                    codeRuleCache.cacheCodeRule(rule);
                    return rule;
                });
    }

    @Override
    public List<String> batchGenerateCode(CodeRuleParam param, Integer count) {
        validateBatchParam(count);

        List<String> codes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            codes.add(generateCode(param));
        }
        return codes;
    }

    private void validateBatchParam(Integer count) {
        if (count == null || count <= 0 || count > 1000) {
            throw new IllegalArgumentException("生成数量必须大于0且不超过1000");
        }
    }

    @Override
    @Transactional
    public boolean addCodeRule(CodeRuleDto codeRuleDto) {
        if (codeRuleDto == null) {
            throw new IllegalArgumentException("编码规则不能为空");
        }
        
        // 检查规则编码是否已存在
        Optional<CodeRule> existingRule = codeRepository.findByRuleCode(codeRuleDto.getRuleCode());
        if (existingRule.isPresent()) {
            throw new IllegalArgumentException("编码规则已存在：" + codeRuleDto.getRuleCode());
        }
        
        // DTO转换为实体
        CodeRule codeRule = modelMapper.map(codeRuleDto, CodeRule.class);
        
        // 设置默认值
        setDefaultValues(codeRule);
        
        // 保存到数据库
        codeRepository.save(codeRule);
        
        // 更新缓存
        codeRuleCache.cacheCodeRule(codeRule);
        
        return true;
    }

    private void setDefaultValues(CodeRule codeRule) {
        if (codeRule.getCurrentSequence() == null) {
            codeRule.setCurrentSequence(1);
        }
        if (codeRule.getStep() == null) {
            codeRule.setStep(1);
        }
        if (codeRule.getStatus() == null) {
            codeRule.setStatus("1");
        }
    }

    @Override
    @Transactional
    public boolean updateCodeRule(CodeRuleDto codeRuleDto) {
        if (codeRuleDto == null || codeRuleDto.getId() == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 获取现有规则
        CodeRule existingCodeRule = getExistingCodeRule(codeRuleDto.getId());
        
        // 更新规则
        modelMapper.map(codeRuleDto, existingCodeRule);
        
        // 保存到数据库
        codeRepository.save(existingCodeRule);
        
        // 更新缓存
        codeRuleCache.cacheCodeRule(existingCodeRule);
        
        return true;
    }

    private CodeRule getExistingCodeRule(Long id) {
        Optional<CodeRule> optionalCodeRule = codeRepository.findById(id);
        return optionalCodeRule.orElseThrow(() ->
                new IllegalArgumentException("编码规则不存在：" + id));
    }

    @Override
    @Transactional
    public boolean deleteCodeRule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 检查规则是否存在
        CodeRule codeRule = getExistingCodeRule(id);
        
        // 从缓存移除
        codeRuleCache.deleteCodeRule(codeRule.getRuleCode());
        
        // 从数据库删除
        return codeRepository.deleteById(id);
    }

    @Override
    public CodeRuleDto getCodeRule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        CodeRule codeRule = getExistingCodeRule(id);
        return modelMapper.map(codeRule, CodeRuleDto.class);
    }

    @Override
    public CodeRuleDto getCodeRuleByCode(String ruleCode) {
        if (ruleCode == null || ruleCode.isEmpty()) {
            throw new IllegalArgumentException("编码规则编码不能为空");
        }
        
        CodeRule codeRule = getAndCacheCodeRule(ruleCode);
        return modelMapper.map(codeRule, CodeRuleDto.class);
    }

    @Override
    public List<CodeRuleDto> listCodeRules(CodeRuleDto codeRuleDto) {
        CodeRule codeRule = codeRuleDto != null ? modelMapper.map(codeRuleDto, CodeRule.class) : null;
        List<CodeRule> codeRules = codeRepository.findByCondition(codeRule);
        
        return codeRules.stream()
                .map(rule -> modelMapper.map(rule, CodeRuleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean resetSequence(Long id, Integer sequence) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        if (sequence == null || sequence < 0) {
            throw new IllegalArgumentException("序列值不能为空或小于0");
        }
        
        // 检查规则是否存在
        CodeRule codeRule = getExistingCodeRule(id);
        
        // 更新序列值
        boolean updated = codeRepository.updateSequence(id, sequence);
        
        if (updated) {
            // 更新缓存中的序列值
            codeRule.setCurrentSequence(sequence);
            codeRuleCache.cacheCodeRule(codeRule);
        }
        
        return updated;
    }

    @Override
    @Transactional
    public boolean enableCodeRule(Long id) {
        return updateCodeRuleStatus(id, "1");
    }

    @Override
    @Transactional
    public boolean disableCodeRule(Long id) {
        return updateCodeRuleStatus(id, "0");
    }

    private boolean updateCodeRuleStatus(Long id, String status) {
        if (id == null) {
            throw new IllegalArgumentException("编码规则ID不能为空");
        }
        
        // 检查规则是否存在
        CodeRule codeRule = getExistingCodeRule(id);
        
        // 更新状态
        boolean updated = codeRepository.updateStatus(id, status);
        
        if (updated) {
            // 更新缓存中的状态
            codeRule.setStatus(status);
            codeRuleCache.cacheCodeRule(codeRule);
        }
        
        return updated;
    }
} 