//package com.vulcan.code.config;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
///**
// * 编码规则配置类
// *
// * @author Y
// */
//@Configuration
//@EnableJpaRepositories(basePackages = "com.vulcan.code.repository")
//@EnableTransactionManagement
//public class CodeRuleConfiguration {
//
//    /**
//     * 注册ModelMapper bean
//     */
//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
//}