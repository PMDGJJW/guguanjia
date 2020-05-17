package com.config;


import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.aspect.SysLogAspect;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * @auth jian j w
 * @date 2020/4/7 17:23
 * @Description
 */
@Configuration
@ComponentScan(basePackages = "com.service")
@EnableTransactionManagement
@EnableAspectJAutoProxy//Aop注解支持
public class SpringService {

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SysLogAspect getSysLogAspect(){
        return new SysLogAspect();
    }

    @Bean(name="druidStatInterceptor")//设置druid 的 aop切面类
    public DruidStatInterceptor getDruidStatInterceptor(){
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        return druidStatInterceptor;
    }

    @Bean//配置spring监控
    public BeanNameAutoProxyCreator getAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setBeanNames(new String[]{"*Mapper","*ServiceImpl"});
        beanNameAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanNameAutoProxyCreator;
    }
}
