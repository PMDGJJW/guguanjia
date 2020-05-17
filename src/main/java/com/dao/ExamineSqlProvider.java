package com.dao;



import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * @auth jian j w
 * @date 2020/4/14 17:52
 * @Description
 */
public class    ExamineSqlProvider {
    public String selectByCondition( Map<String,Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " ex.*, " +
                " su.NAME user_name, " +
                " so.NAME office_name  " +
                "FROM " +
                " examine ex, " +
                " sys_user su, " +
                " sys_office so  " +
                "WHERE " +
                " ex.del_flag = 0  " +
                " AND su.del_flag = 0  " +
                " AND so.del_flag = 0 ");

        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
            sb.append(" AND ex.type =#{type} ");
        }

        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))) {
            sb.append(" AND su.NAME LIKE concat( '%', #{name}, '%' ) ");
        }

        if (params.containsKey("officeId") && !StringUtils.isEmpty(params.get("officeId"))) {
            sb.append(" AND so.id =#{officeId} ");
        }

        sb.append("AND ex.examine_user_id = su.id  " +
                " AND su.office_id = so.id");
        return sb.toString();

    }
}
