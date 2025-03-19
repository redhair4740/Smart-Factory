package com.vulcan.code.repository;

import com.vulcan.domain.entity.po.CodeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 编码规则JPA存储库
 *
 * @author Y
 */
@Repository
public interface CodeRuleJpaRepository extends JpaRepository<CodeRule, Long>, JpaSpecificationExecutor<CodeRule> {

    /**
     * 根据规则编码查询编码规则
     *
     * @param ruleCode 规则编码
     * @return 编码规则
     */
    Optional<CodeRule> findByRuleCode(String ruleCode);

    /**
     * 更新序列值
     *
     * @param id 编码规则ID
     * @param sequence 序列值
     * @return 影响行数
     */
    @Modifying
    @Query("UPDATE CodeRule c SET c.currentSequence = :sequence, c.updateTime = CURRENT_TIMESTAMP WHERE c.id = :id")
    int updateSequence(@Param("id") Long id, @Param("sequence") Long sequence);

    /**
     * 更新状态
     *
     * @param id 编码规则ID
     * @param status 状态
     * @return 影响行数
     */
    @Modifying
    @Query("UPDATE CodeRule c SET c.status = :status, c.updateTime = CURRENT_TIMESTAMP WHERE c.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 增加序列值
     *
     * @param id 编码规则ID
     * @param step 步长
     * @return 影响行数
     */
    @Modifying
    @Query("UPDATE CodeRule c SET c.currentSequence = c.currentSequence + :step, c.updateTime = CURRENT_TIMESTAMP WHERE c.id = :id")
    int increaseSequence(@Param("id") Long id, @Param("step") Integer step);
} 