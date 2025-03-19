package com.vulcan.code.controller;

import com.vulcan.domain.entity.ResultVo;
import com.vulcan.domain.entity.dto.CodeRuleDto;
import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.code.service.CodeRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 编码规则控制器
 *
 * @author Y
 */
@RestController
@RequestMapping("/code/rule")
@RequiredArgsConstructor
public class CodeRuleController {

    private final CodeRuleService codeRuleService;

    /**
     * 生成编码
     *
     * @param param 参数
     * @return 编码
     */
    @PostMapping("/generate")
    public ResultVo<String> generateCode(@RequestBody CodeRuleParam param) {
        return ResultVo.success(codeRuleService.generateCode(param));
    }

    /**
     * 批量生成编码
     *
     * @param param 参数
     * @param count 数量
     * @return 编码列表
     */
    @PostMapping("/generate/batch")
    public ResultVo<List<String>> batchGenerateCode(@RequestBody CodeRuleParam param, @RequestParam Integer count) {
        return ResultVo.success(codeRuleService.batchGenerateCode(param, count));
    }

    /**
     * 添加编码规则
     *
     * @param codeRuleDto 编码规则
     * @return 添加结果
     */
    @PostMapping
    public ResultVo<Boolean> addCodeRule(@RequestBody CodeRuleDto codeRuleDto) {
        return ResultVo.success(codeRuleService.addCodeRule(codeRuleDto));
    }

    /**
     * 修改编码规则
     *
     * @param codeRuleDto 编码规则
     * @return 修改结果
     */
    @PutMapping
    public ResultVo<Boolean> updateCodeRule(@RequestBody CodeRuleDto codeRuleDto) {
        return ResultVo.success(codeRuleService.updateCodeRule(codeRuleDto));
    }

    /**
     * 删除编码规则
     *
     * @param id 编码规则ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResultVo<Boolean> deleteCodeRule(@PathVariable Long id) {
        return ResultVo.success(codeRuleService.deleteCodeRule(id));
    }

    /**
     * 获取编码规则
     *
     * @param id 编码规则ID
     * @return 编码规则
     */
    @GetMapping("/{id}")
    public ResultVo<CodeRuleDto> getCodeRule(@PathVariable Long id) {
        return ResultVo.success(codeRuleService.getCodeRule(id));
    }

    /**
     * 根据规则编码获取编码规则
     *
     * @param ruleCode 规则编码
     * @return 编码规则
     */
    @GetMapping("/code/{ruleCode}")
    public ResultVo<CodeRuleDto> getCodeRuleByCode(@PathVariable String ruleCode) {
        return ResultVo.success(codeRuleService.getCodeRuleByCode(ruleCode));
    }

    /**
     * 获取编码规则列表
     *
     * @param codeRuleDto 查询条件
     * @return 编码规则列表
     */
    @PostMapping("/list")
    public ResultVo<List<CodeRuleDto>> listCodeRules(@RequestBody CodeRuleDto codeRuleDto) {
        return ResultVo.success(codeRuleService.listCodeRules(codeRuleDto));
    }

    /**
     * 重置编码规则序列
     *
     * @param id 编码规则ID
     * @param sequence 序列值
     * @return 重置结果
     */
    @PutMapping("/{id}/sequence/{sequence}")
    public ResultVo<Boolean> resetSequence(@PathVariable Long id, @PathVariable Long sequence) {
        return ResultVo.success(codeRuleService.resetSequence(id, sequence));
    }

    /**
     * 启用编码规则
     *
     * @param id 编码规则ID
     * @return 启用结果
     */
    @PutMapping("/{id}/enable")
    public ResultVo<Boolean> enableCodeRule(@PathVariable Long id) {
        return ResultVo.success(codeRuleService.enableCodeRule(id));
    }

    /**
     * 禁用编码规则
     *
     * @param id 编码规则ID
     * @return 禁用结果
     */
    @PutMapping("/{id}/disable")
    public ResultVo<Boolean> disableCodeRule(@PathVariable Long id) {
        return ResultVo.success(codeRuleService.disableCodeRule(id));
    }
}