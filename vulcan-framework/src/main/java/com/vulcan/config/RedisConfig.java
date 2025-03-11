package com.vulcan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.config
 * @name: RedisConfig
 * @Date: 2024/4/12  上午10:46
 * @Description Redis配置类，用于从配置文件中读取Redis连接参数，支持Redis缓存服务
 */
@Data
@Configuration
@PropertySource(value = "application.yml")
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    private int maxActive;

    private int maxWait;

    private int maxIdle;

    private int timeout;

    private int database;

    private int maxTotal;

    private int maxWaitMillis;
}
