package com.dao;

import com.entity.SysLog;
import org.springframework.util.StringUtils;

/**
 * @auth jian j w
 * @date 2020/5/2 22:33
 * @Description
 */
public class SysLogSqlProvider {

    public String selectByCondition(SysLog params){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT " +
                " sl.id, " +
                " sl.type, " +
                " sl.create_by, " +
                " sl.create_date, " +
                " sl.remote_addr, " +
                " sl.user_agent, " +
                " sl.request_uri, " +
                " sl.method, " +
                " sl.params, " +
                " sl.exception, " +
                " sl.description " +
                " FROM " +
                " sys_log  sl " );
        if (params.getType()!=null && !StringUtils.isEmpty(params.getType())){
            sb.append(" WHERE sl.type = #{type}");
        }
        return sb.toString();
    }
}
