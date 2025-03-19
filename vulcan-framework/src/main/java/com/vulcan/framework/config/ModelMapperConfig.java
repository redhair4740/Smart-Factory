package com.vulcan.framework.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper配置类
 * 
 * @author Y
 */
@Configuration
public class ModelMapperConfig {

    /**
     * 配置ModelMapper Bean
     * 
     * @return ModelMapper实例
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // 配置严格的匹配策略，字段名必须完全匹配
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)   // 跳过空值
                .setFieldMatchingEnabled(true)  // 启用字段匹配
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // 允许访问私有字段
        
        return modelMapper;
    }
} 