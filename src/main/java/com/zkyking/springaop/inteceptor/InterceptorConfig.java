package com.zkyking.springaop.inteceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2020-01-09
 * Time: 14:43:05
 * Description:
 * 登录拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer
{
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}
