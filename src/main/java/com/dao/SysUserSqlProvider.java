package com.dao;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/25 10:24
 * @Description
 */
public class SysUserSqlProvider {
    public String selectByCondition (Map<String,Object>params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " su.*, " +
                " so.`name` company_name , " +
                " sr.`id` role_id ," +
                " sur.id user_role_id " +
                "FROM " +
                " sys_user su " +
                " left join  " +
                " sys_office so " +
                " on su.office_id = so.id " +
                " left join  " +
                " sys_user_role sur  " +
                " on su.id = sur.user_id " +
                " left join  " +
                " sys_role sr " +
                " on  " +
                " sur.role_id = sr.id " +
                " where  " +
                " su.del_flag=0 " );
//                " and so.del_flag = 0 " +
//                " and sr.del_flag = 0 " +
//                " and sur.del_flag = 0 ");
        if (params.containsKey("id") && !StringUtils.isEmpty(params.get("id"))){
            sb.append(" and su.id=#{id} ");
        }
        if (params.containsKey("oid") && !StringUtils.isEmpty(params.get("oid"))){
            sb.append(" and so.id=#{oid} ");
        }
        if (params.containsKey("realName") && !StringUtils.isEmpty(params.get("realName"))){
            sb.append(" and su.name like CONCAT('%',#{realName},'%') ");
        }
        if (params.containsKey("rid") && !StringUtils.isEmpty(params.get("rid"))){
            sb.append(" and sr.id = #{rid} ");
        }
        if (params.containsKey("no") && !StringUtils.isEmpty(params.get("no"))){
            sb.append(" and su.`no`= #{no} ");
        }
        return sb.toString();
    }
}
