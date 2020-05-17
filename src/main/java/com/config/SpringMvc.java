package com.config;

import com.Inceptor.LoginInceptor;
import com.Inceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @auth jian j w
 * @date 2020/4/7 19:59
 * @Description
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.controller")
public class SpringMvc implements WebMvcConfigurer {

    @Autowired
    private ResourceInterceptor resourceInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    @Bean
    public InternalResourceViewResolver getView(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/html");
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        return new CommonsMultipartResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInceptor loginInceptor = new LoginInceptor();
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInceptor);
        interceptorRegistration.addPathPatterns("/manager/**","/main/**");//设置拦截逻辑
        interceptorRegistration.excludePathPatterns("/main/doLogin");//设置放行规则

        //必须被spring容器创建管理  必须先于当前代码创建
        InterceptorRegistration resourceRegistration = registry.addInterceptor(resourceInterceptor);
        resourceRegistration.addPathPatterns("/manager/**","/main/**");//设置拦截逻辑
        resourceRegistration.excludePathPatterns("/main/doLogin");//设置放行规则
    }
}
