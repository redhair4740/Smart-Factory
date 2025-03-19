-- 创建编码规则表
CREATE TABLE IF NOT EXISTS sys_code_rule (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    rule_code VARCHAR(64) NOT NULL COMMENT '规则编码（唯一标识）',
    rule_name VARCHAR(100) NOT NULL COMMENT '规则名称',
    prefix VARCHAR(32) DEFAULT NULL COMMENT '规则前缀',
    date_format VARCHAR(32) DEFAULT NULL COMMENT '日期格式',
    sequence_length INT(11) NOT NULL COMMENT '流水号长度',
    current_sequence BIGINT(20) NOT NULL COMMENT '当前序列值',
    is_cycle INT(1) NOT NULL COMMENT '是否循环（0-否；1-是）',
    cycle_rule VARCHAR(20) NOT NULL COMMENT '循环规则（每日循环-DAY；每月循环-MONTH；每年循环-YEAR；不循环-NONE）',
    max_value BIGINT(20) DEFAULT NULL COMMENT '最大值（循环时的最大值）',
    step INT(11) NOT NULL COMMENT '步长',
    app_id VARCHAR(32) DEFAULT NULL COMMENT '应用ID（可选，用于区分不同应用的编码）',
    status INT(1) NOT NULL COMMENT '状态（0-禁用；1-启用）',
    remark VARCHAR(200) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (id),
    UNIQUE INDEX uniq_rule_code (rule_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='编码规则表';

-- 初始化示例数据
INSERT INTO sys_code_rule (rule_code, rule_name, prefix, date_format, sequence_length, current_sequence, is_cycle, cycle_rule, max_value, step, app_id, status, remark, create_time, create_by, update_time, update_by)
VALUES 
('ORDER', '订单编码规则', 'ORD', 'yyyyMMdd', 5, 1, 1, 'DAY', 99999, 1, NULL, 1, '订单编码规则，每日循环', NOW(), 'admin', NOW(), 'admin'),
('CUSTOMER', '客户编码规则', 'CUST', 'yyyyMM', 4, 1, 1, 'MONTH', 9999, 1, NULL, 1, '客户编码规则，每月循环', NOW(), 'admin', NOW(), 'admin'),
('PRODUCT', '产品编码规则', 'P', NULL, 6, 1, 0, 'NONE', NULL, 1, NULL, 1, '产品编码规则，不循环', NOW(), 'admin', NOW(), 'admin'); 