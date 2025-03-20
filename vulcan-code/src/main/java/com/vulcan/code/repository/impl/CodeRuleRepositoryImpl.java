package com.vulcan.code.repository.impl;

import com.vulcan.code.repository.CodeRuleJpaRepository;
import com.vulcan.domain.entity.po.CodeRule;
import com.vulcan.domain.repository.CodeQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 编码规则仓库实现 - 领域仓库
 *
 * @author Y
 */
@Repository
@RequiredArgsConstructor
public class CodeRuleRepositoryImpl implements CodeQueryRepository {

    private final CodeRuleJpaRepository codeRuleJpaRepository;
    
    @Override
    @Transactional
    public CodeRule save(CodeRule codeRule) {
        return codeRuleJpaRepository.save(codeRule);
    }

    @Override
    public Optional<CodeRule> findById(Long id) {
        return codeRuleJpaRepository.findById(id);
    }

    @Override
    public Optional<CodeRule> findByRuleCode(String ruleCode) {
        return codeRuleJpaRepository.findByRuleCode(ruleCode);
    }

    @Override
    public List<CodeRule> findAll() {
        return codeRuleJpaRepository.findAll();
    }

    @Override
    public List<CodeRule> findByCondition(CodeRule codeRule) {
        return codeRuleJpaRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (codeRule != null) {
                // 规则编码
                if (StringUtils.isNotBlank(codeRule.getRuleCode())) {
                    predicates.add(builder.like(root.get("ruleCode"), "%" + codeRule.getRuleCode() + "%"));
                }
                
                // 规则名称
                if (StringUtils.isNotBlank(codeRule.getRuleName())) {
                    predicates.add(builder.like(root.get("ruleName"), "%" + codeRule.getRuleName() + "%"));
                }
                
                // 应用ID
                if (StringUtils.isNotBlank(codeRule.getAppId())) {
                    predicates.add(builder.equal(root.get("appId"), codeRule.getAppId()));
                }
                
                // 状态
                if (codeRule.getStatus() != null) {
                    predicates.add(builder.equal(root.get("status"), codeRule.getStatus()));
                }
            }
            
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        try {
            codeRuleJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateSequence(Long id, Long sequence) {
        return codeRuleJpaRepository.updateSequence(id, sequence) > 0;
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id, Integer status) {
        return codeRuleJpaRepository.updateStatus(id, status) > 0;
    }
} 