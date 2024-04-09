package com.vulcan.annotation;

import java.lang.annotation.*;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.annotation
 * @name: Log
 * @Date: 2024/4/7  上午11:55
 * @Description 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};
}
