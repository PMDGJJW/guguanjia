package com.Inceptor;

import com.entity.SysResource;
import com.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 后端权限认证：
 * 1.登录成功后，绑定用户权限到状态对象
 * 2.任意请求到达，进行权限认证:
 * a.如果请求权限不是在系统所有限制权限范围，放行（公共资源/按钮级别资源）
 * b.如果请求权限是在系统所有权限范围:
 * 1)获取用户权限  判断是否拥有访问权限，有则放行
 * 2)如果不是用户拥有权限范围，则阻止访问
 */
public class ResourceInterceptor implements HandlerInterceptor {

    /**
     * service对象都是tomcat容器启动的时候，加载spring父容器对象创建的
     * ResourceInterceptor对象是配置在SpringMvc配置类中
     */
    @Autowired
    SysResourceService resourceService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//获取用户请求地址   /应用名/请求      数据库保存url:  请求
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replaceAll(request.getContextPath()+"/","");

        List<SysResource> systemResources = resourceService.selectAll();//系统所有需要认证的资源
        boolean flag = false;//需要授权标记  默认false
        for (SysResource systemResource : systemResources) {
            if(!StringUtils.isEmpty(systemResource.getUrl())&&requestURI.equals(systemResource.getUrl())){
                //需要认证的资源
                flag = true;
                break;
            }
        }

        if(flag){//需要认证
            //1)获取用户权限  判断是否拥有访问权限，有则放行
            // * 2)如果不是用户拥有权限范围，则阻止访问
            List<SysResource> userResources = (List<SysResource>) request.getSession().getAttribute("resources");

            if(userResources.size()>0) {
                for (SysResource userResource : userResources) {
                    if(!StringUtils.isEmpty(userResource.getUrl())&&requestURI.equals(userResource.getUrl())){
                        //放行资源  用户  认证成功
                        return true;
                    }
                }
            }
        }else{
//            放行
            return true;
        }
        //没有权限统一跳转到  没有权限访问页
        request.getRequestDispatcher("/notauth.html").forward(request,response);
        return false;
    }
}
