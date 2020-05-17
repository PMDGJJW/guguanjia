package com.dao;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/15 13:22
 * @Description 电子台账动态SQL
 */
public class WorkOrderSqlProvider {
    public String selectByCondition(Map<String,Object>params){
        StringBuilder sb = new StringBuilder();
        sb.append(" select wo.*, " +
                " cu.name create_user_name,cu.phone create_user_phone,co.name create_office_name, " +
                " tu.name transport_user_name,tu.phone transport_user_phone,`to`.name transport_office_name, " +
                " ru.name recipient_user_name,ru.phone recipient_user_phone,ro.name recipient_office_name " +
                " from " +
                " work_order wo " +
                " left join " +
                " sys_user cu " +
                " on " +
                " wo.create_user_id=cu.id " +
                " left join " +
                " sys_office co " +
                " on " +
                " cu.office_id=co.id " +
                " left join " +
                " sys_user tu  " +
                " on " +
                " wo.transport_user_id=tu.id " +
                " left join " +
                " sys_office `to`  " +
                " on " +
                " tu.office_id=`to`.id " +
                " left join " +
                " sys_user ru " +
                " on " +
                " wo.recipient_user_id=ru.id " +
                " left join " +
                " sys_office ro " +
                " on " +
                " ru.office_id=ro.id " +
                " where " +
                " wo.del_flag=0 " +
                "and cu.del_flag=0 " +
                "and co.del_flag=0 ");
        if (params.containsKey("startDate") && !StringUtils.isEmpty(params.get("startDate"))) {
            sb.append(" AND wo.create_date >=#{startDate} ");
        }
        if (params.containsKey("endDate") && !StringUtils.isEmpty(params.get("endDate"))) {
            sb.append(" AND wo.create_date <#{endDate} ");
        }
        if (params.containsKey("status") && !StringUtils.isEmpty(params.get("status"))) {
            sb.append(" AND wo.status=#{status} ");
        }
        if (params.containsKey("officeId") && !StringUtils.isEmpty(params.get("officeId"))) {
            sb.append(" AND (co.id = #{officeId} or `to`.id=#{officeId} or ro.id = #{officeId})");
        }
        return sb.toString();
    }
}
