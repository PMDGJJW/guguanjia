package com.config;

import com.Inceptor.ResourceInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @auth jian j w
 * @date 2020/4/7 16:20
 * @Description
 */
@Configuration
@MapperScan(basePackages = "com.dao")
@PropertySource(value = "classpath:sys.properties",encoding = "utf-8")
@Import(value = {SpringService.class,SpringCacheConfig.class})
public class SpringMybatis {
    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        Properties properties = new Properties();
        try {
            properties.load(SpringMybatis.class.getClassLoader().getResourceAsStream("db.properties"));
            dataSource.configFromPropety(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return dataSource;
    }
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Log4j2Impl.class);
        //指定试用可选参数配置类
        sqlSessionFactoryBean.setConfiguration(configuration);

        //设置分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();//分页拦截对象
        pageInterceptor.setProperties(new Properties());//设置参数，用于生成默认的方言
        sqlSessionFactoryBean.setPlugins(pageInterceptor);

        return sqlSessionFactoryBean;
    }

    @Bean
    public ResourceInterceptor getResourceInterceptor(){
        return new ResourceInterceptor();
    }
}
