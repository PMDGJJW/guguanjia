package com.dao;

import com.entity.QualificationCongition;
import org.springframework.util.StringUtils;

/**
 * @auth jian j w
 * @date 2020/4/13 17:16
 * @Description
 */
public class QualificationSqlProvider {

    public String selectCondition(QualificationCongition congition){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " qu.*, " +
                " su.NAME user_name, " +
                " cu.NAME check_user_name  " +
                "FROM " +
                " qualification qu " +
                " LEFT JOIN sys_user su ON qu.upload_user_id = su.id " +
                " LEFT JOIN sys_user cu ON qu.check_user_id = cu.id  " +
                "WHERE " +
                " qu.del_flag = '0'  " +
                " AND su.del_flag = '0' ");
        if (!StringUtils.isEmpty(congition.getCheck())){
            sb.append("AND qu.CHECK = #{check} ");
        }
        if(!StringUtils.isEmpty(congition.getType())){
            sb.append(" AND qu.type =#{type} ");
        }
        if(!StringUtils.isEmpty(congition.getStartDate())){
            sb.append(" AND DATE(qu.create_date) >=#{startDate} ");
        }
        if(!StringUtils.isEmpty(congition.getEndDate())){
            sb.append(" AND DATE(qu.create_date) <=#{endDate} ");
        }

        return sb.toString();
    }

}
