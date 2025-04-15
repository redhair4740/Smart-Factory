package com.vulcan.code.generator;

import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.po.CodeRule;

/**
 * 编码生成器接口
 *
 * @author Y
 */
public interface CodeGenerator {

    /**
     * 生成编码
     *
     * @param codeRule 编码规则
     * @param param 参数
     * @return 生成的编码
     */
    String generate(CodeRule codeRule, CodeRuleParam param);

    /**
     * 获取下一个序列值
     *
     * @param codeRule 编码规则
     * @return 下一个序列值
     */
    Integer getNextSequence(CodeRule codeRule);

    /**
     * 生成序列号部分
     *
     * @param sequence 序列值
     * @param length 长度
     * @return 序列号
     */
    String formatSequence(Integer sequence, Integer length);
} 