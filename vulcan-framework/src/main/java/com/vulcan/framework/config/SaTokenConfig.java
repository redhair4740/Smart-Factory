package com.vulcan.framework.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* Sa-Token配置类
* @author Y
*/
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 登录校验 - 拦截所有路由，并排除登录接口和注册相关接口
            SaRouter.match("/**")
                    .notMatch("/auth/login")        // 登录接口
                    .notMatch("/sys/user/register") // 用户注册接口
                    .notMatch("/sys/user/check-username") // 检查用户名接口
                    .notMatch("/doc.html")          // Swagger文档
                    .notMatch("/swagger-ui.html")   // Swagger UI
                    .notMatch("/swagger-resources/**") // Swagger资源
                    .notMatch("/webjars/**")        // Swagger依赖
                    .notMatch("/v3/api-docs/**")    // OpenAPI文档
                    .notMatch("/static/**")         // 静态资源
                    .notMatch("/error")             // 错误页面
                    .check(r -> StpUtil.checkLogin());

            // 更多路由拦截配置...
        })).addPathPatterns("/**");
    }


//    /**
//     * Sa-Token 整合 jwt (无状态模式)
//     */
//    @Bean
//    public StpLogic getStpLogicJwt() {
//        return new StpLogicJwtForStateless();
//    }
}