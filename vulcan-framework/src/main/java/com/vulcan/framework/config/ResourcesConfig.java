package com.vulcan.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @author Y
* @Project: Smart-Factory
* @Package: com.vulcan.config
* @name: ResourcesConfig
* @Date: 2024/4/12  上午10:46
* @Description // 通用配置
*/
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{
   /**
    * 跨域配置
    */
   @Bean
   public CorsFilter corsFilter()
   {
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowCredentials(true);
       // 设置访问源地址
       config.addAllowedOriginPattern("*");
       // 设置访问源请求头
       config.addAllowedHeader("*");
       // 设置访问源请求方法
       config.addAllowedMethod("*");
       // 有效期 1800秒
       config.setMaxAge(1800L);
       // 添加映射路径，拦截一切请求
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", config);
       // 返回新的CorsFilter
       return new CorsFilter(source);
   }
}