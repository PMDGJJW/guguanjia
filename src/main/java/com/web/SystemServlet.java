package com.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 德鲁伊性能监控（连接池自带功能）：
 * 1.在德鲁伊配置添加拦截器
 * 2.编写一个servlet，实现继承druid的StatViewServlet,自动生成监控页面
 */
@WebServlet(value = "/druid/*",initParams = {
        @WebInitParam(name = "loginUsername",value = "druid"),//登录监控页的账户
        @WebInitParam(name = "loginPassword",value = "druid")//密码
//        @WebInitParam(name = "allow",value = ""),//白名单
//        @WebInitParam(name = "deny",value = ""),//黑名单
//        @WebInitParam(name = "",value = ""),
//        @WebInitParam(name = "",value = "")
})
public class SystemServlet extends StatViewServlet {
}
