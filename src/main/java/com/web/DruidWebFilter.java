package com.web;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 开启druid 的web 功能监控filter
 */

@WebFilter(value = "/*",initParams = {
        @WebInitParam(name="sessionStatEnable",value = "true"),//监控session情况
        @WebInitParam(name="exclusions",value = "*.js,*.jpg,*.png,*.css,/druid/*")//设置忽略拦截规则
        //profileEnable 能够监控单个url调用的sql列表
})
public class DruidWebFilter extends WebStatFilter {
}
