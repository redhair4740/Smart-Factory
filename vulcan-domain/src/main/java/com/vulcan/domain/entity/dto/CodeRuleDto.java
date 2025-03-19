package com.vulcan.domain.entity.dto;

import com.vulcan.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 编码规则数据传输对象
 *
 * @author Y
 */
@Data
public class CodeRuleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 规则编码（唯一标识）
     */
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则前缀
     */
    private String prefix;

    /**
     * 日期格式
     */
    private String dateFormat;

    /**
     * 流水号长度
     */
    private Integer sequenceLength;

    /**
     * 当前序列值
     */
    private Long currentSequence;

    /**
     * 是否循环（0-否；1-是）
     */
    private Integer isCycle;

    /**
     * 循环规则（每日循环-DAY；每月循环-MONTH；每年循环-YEAR；不循环-NONE）
     */
    private String cycleRule;

    /**
     * 最大值（循环时的最大值）
     */
    private Long maxValue;

    /**
     * 步长
     */
    private Integer step;

    /**
     * 应用ID（可选，用于区分不同应用的编码）
     */
    private String appId;

    /**
     * 状态（0-禁用；1-启用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNumber;
} 