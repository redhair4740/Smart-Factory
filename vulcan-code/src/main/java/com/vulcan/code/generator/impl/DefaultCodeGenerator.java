package com.vulcan.code.generator.impl;

import com.vulcan.code.generator.CodeGenerator;
import com.vulcan.code.repository.CodeRuleJpaRepository;
import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.po.CodeRule;
import com.vulcan.domain.enums.CycleTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 默认编码生成器实现
 *
 * @author Y
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultCodeGenerator implements CodeGenerator {

    private final CodeRuleJpaRepository codeRuleJpaRepository;
    private final RedissonClient redissonClient;

    /**
     * 分布式锁前缀
     */
    private static final String LOCK_KEY_PREFIX = "code_generator:lock:";

    @Override
    public String generate(CodeRule codeRule, CodeRuleParam param) {
        StringBuilder code = new StringBuilder();

        // 添加前缀
        if (codeRule.getPrefix() != null && !codeRule.getPrefix().isEmpty()) {
            code.append(codeRule.getPrefix());
        }

        // 添加日期格式
        if (codeRule.getDateFormat() != null && !codeRule.getDateFormat().isEmpty()) {
            LocalDateTime now;
            if (param.getCurrentDate() != null && !param.getCurrentDate().isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(param.getCurrentDate());
                    now = date.atStartOfDay();
                } catch (Exception e) {
                    log.warn("Invalid date format: {}, using current date", param.getCurrentDate());
                    now = LocalDateTime.now();
                }
            } else {
                now = LocalDateTime.now();
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(codeRule.getDateFormat());
            code.append(now.format(formatter));
        }

        // 获取序列号并格式化
        Integer sequence = getNextSequence(codeRule);
        String sequenceStr = formatSequence(sequence, codeRule.getSequenceLength());
        code.append(sequenceStr);

        return code.toString();
    }

    @Override
    @Transactional
    public Integer getNextSequence(CodeRule codeRule) {
        String lockKey = LOCK_KEY_PREFIX + codeRule.getRuleCode();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试获取锁，最多等待2秒，锁过期时间10秒
            boolean isLocked = lock.tryLock(2, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new RuntimeException("获取分布式锁失败，无法生成编码");
            }

            // 重新获取最新的编码规则
            CodeRule latestCodeRule = codeRuleJpaRepository.findById(codeRule.getId())
                    .orElseThrow(() -> new RuntimeException("编码规则不存在：" + codeRule.getRuleCode()));

            Integer currentSequence = latestCodeRule.getCurrentSequence();
            Integer step = latestCodeRule.getStep();
            
            // 计算下一个序列值
            Integer nextSequence = currentSequence + step;
            
            // 处理循环规则
            if (latestCodeRule.getIsCycle() == 1 && latestCodeRule.getMaxValue() != null) {
                // 如果超出最大值，则重置为1
                if (nextSequence > latestCodeRule.getMaxValue()) {
                    nextSequence = 1;
                }
            }
            
            // 更新数据库中的序列值
            codeRuleJpaRepository.updateSequence(latestCodeRule.getId(), nextSequence);
            
            return currentSequence;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("获取编码序列值被中断", e);
        } catch (Exception e) {
            throw new RuntimeException("获取编码序列值失败", e);
        } finally {
            // 如果当前线程持有锁，则释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public String formatSequence(Integer sequence, Integer length) {
        return String.format("%0" + length + "d", sequence);
    }
} 