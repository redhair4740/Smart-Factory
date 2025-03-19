package com.vulcan.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Jackson全局配置类
 * 主要用于Web请求响应的JSON序列化配置
 *
 * @author Y
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置Web请求响应使用的ObjectMapper
     * 禁用类型信息输出，使得JSON响应为标准的对象格式，而不是带类型信息的数组
     */
    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 禁用将日期类型格式化为时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // 创建HTTP消息转换器，这会覆盖Spring MVC默认的Jackson配置
        // 而不会影响缓存等其他模块使用的ObjectMapper配置
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
} 