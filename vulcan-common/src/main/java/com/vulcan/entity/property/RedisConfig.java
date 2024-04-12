package com.vulcan.entity.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.property
 * @name: RedisConfig
 * @Date: 2024/4/12  上午10:46
 * @Description //TODO
 */
@Data
@Configuration
@PropertySource("application.yml")
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    private int maxActive;

    private int maxWait;

    private int maxIdle;

    private int mimIdle;

    private int timeout;

    private int database;

    private int maxTotal;

    private int maxWaitMillis;
}
