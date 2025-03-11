package com.vulcan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan
 * @name: BootAdminApplication
 * @Date: 2024/4/7
 * @Description 伏尔甘智能工厂系统启动类
 *             采用模块化架构，各模块职责分明：
 *             - vulcan-admin: 仅包含启动类，作为应用入口
 *             - vulcan-common: 通用工具类和公共组件
 *             - vulcan-framework: 框架核心配置和基础设施
 *             - vulcan-system: 系统管理功能模块
 *             - vulcan-auth: 认证授权模块
 *             - vulcan-mq: 消息队列模块
 */
@SpringBootApplication
public class BootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootAdminApplication.class, args);
    }

}
