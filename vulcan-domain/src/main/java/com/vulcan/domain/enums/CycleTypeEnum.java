package com.vulcan.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 循环类型枚举
 *
 * @author Y
 */
@Getter
@AllArgsConstructor
public enum CycleTypeEnum {

    /**
     * 不循环
     */
    NONE("NONE", "不循环"),

    /**
     * 每日循环
     */
    DAY("DAY", "每日循环"),

    /**
     * 每月循环
     */
    MONTH("MONTH", "每月循环"),

    /**
     * 每年循环
     */
    YEAR("YEAR", "每年循环");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static CycleTypeEnum getByCode(String code) {
        for (CycleTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return NONE;
    }
} 