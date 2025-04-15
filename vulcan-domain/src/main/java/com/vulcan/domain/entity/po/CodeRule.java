package com.vulcan.domain.entity.po;

import com.vulcan.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 编码规则实体类
 *
 * @author Y
 */
@Data
@Entity
@Table(name = "sys_code_rule")
@EqualsAndHashCode(callSuper = true)
public class CodeRule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则编码（唯一标识）
     */
    @Column(name = "rule_code", nullable = false, unique = true, length = 64)
    private String ruleCode;

    /**
     * 规则名称
     */
    @Column(name = "rule_name", nullable = false, length = 100)
    private String ruleName;

    /**
     * 规则前缀
     */
    @Column(name = "prefix", length = 32)
    private String prefix;

    /**
     * 日期格式
     */
    @Column(name = "date_format", length = 32)
    private String dateFormat;

    /**
     * 流水号长度
     */
    @Column(name = "sequence_length", nullable = false)
    private Integer sequenceLength;

    /**
     * 当前序列值
     */
    @Column(name = "current_sequence", nullable = false)
    private Integer currentSequence;

    /**
     * 是否循环（0-否；1-是）
     */
    @Column(name = "is_cycle", nullable = false)
    private Integer isCycle;

    /**
     * 循环规则（每日循环-DAY；每月循环-MONTH；每年循环-YEAR；不循环-NONE）
     */
    @Column(name = "cycle_rule", nullable = false, length = 20)
    private String cycleRule;

    /**
     * 最大值（循环时的最大值）
     */
    @Column(name = "max_value")
    private Integer maxValue;

    /**
     * 步长
     */
    @Column(name = "step", nullable = false)
    private Integer step;

    /**
     * 应用ID（可选，用于区分不同应用的编码）
     */
    @Column(name = "app_id", length = 32)
    private String appId;
} 