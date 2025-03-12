package com.vulcan.framework.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* Sa-Token配置类
* @author Y
*/
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       // 注册Sa-Token的路由拦截器
       registry.addInterceptor(new SaInterceptor(handler -> {
           // 指定需要进行登录验证的路径
           SaRouter.match("/**")
                   // 排除登录接口
                   .notMatch("/auth/login")
                   // 排除静态资源
                   .notMatch("/static/**")
                   // 检查是否登录
                   .check(r -> StpUtil.checkLogin());
       })).addPathPatterns("/**");
   }
}