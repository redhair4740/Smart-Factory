package com.vulcan.domain.entity.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 编码规则参数类
 *
 * @author Y
 */
@Data
public class CodeRuleParam implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 规则编码（唯一标识）
     */
    private String ruleCode;
    
    /**
     * 业务对象ID（用于特定业务场景中绑定对象）
     */
    private String businessId;
    
    /**
     * 应用ID（可选，用于区分不同应用的编码）
     */
    private String appId;
    
    /**
     * 当前日期（可选，默认为系统当前日期）
     */
    private String currentDate;
    
    /**
     * 自定义参数（可用于自定义规则扩展）
     */
    private String customParam;
} 