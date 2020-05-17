package com.aspect;

import com.entity.SysLog;
import com.entity.SysUser;
import com.service.SysLogService;
import com.service.impl.Business.SysLogServiceImpl;
import com.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/5/3 20:42
 * @Description
 */
@Aspect
public class SysLogAspect {

    @Autowired
    SysLogService service;

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(* com.service.impl.Business.*ServiceImpl.*(..))")
    public void pointcut(){}

    @AfterReturning(value = "pointcut()",returning = "obj")
    public Object afterReturning(JoinPoint joinPoint,Object obj){
        if(joinPoint.getTarget().getClass()!= SysLogServiceImpl.class) {
            saveLog(joinPoint, null);
        }
        return obj;
    }

    @AfterThrowing(value ="pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        saveLog(joinPoint,e);
    }

    private void saveLog(JoinPoint joinPoint, Exception e){
        SysLog sysLog = new SysLog();
        sysLog.setType(e==null?"1":"2");//设置操作日志类型  2为异常日志
        sysLog.setException(e==null?"":e.toString());
        if(request!=null){//正常请求
            SysUser loginUser = (SysUser) request.getSession().getAttribute("loginUser");

            if(loginUser!=null){
                sysLog.setCreateBy(loginUser.getName());
            }
            //保存请求相关信息
            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));//获取ip

            sysLog.setUserAgent(request.getHeader("user-agent"));

            sysLog.setRequestUri(request.getRequestURI());

            sysLog.setMethod(request.getMethod());
        }

        Object[] args = joinPoint.getArgs();
        if(args!=null&&args.length>0){
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg!=null){
                    String typeName = arg.getClass().getSimpleName();
                    sb.append("[参数").append(i+1).append(",类型:").append(typeName).append(",值:").append(arg.toString()).append("]");
                }else{
                    sb.append("[参数").append(i+1).append(",值:null]");
                }
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sysLog.setParams(sb.toString());
        }
        sysLog.setCreateDate(new Date());
        service.insertSelective(sysLog);

    }
}
