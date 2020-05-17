package com.dao;

import com.entity.Waste;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/21 18:17
 * @Description 机构管理动态SQL
 */
public class OfficeSqlProvider {

    public String selectOffice(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select so.*,sa.name area_name from sys_office so left join sys_area sa on so.area_id = sa.id where so.del_flag = 0 ");
        if ( params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))){
            sb.append(" and so.name like CONCAT('%',#{name},'%') ");
        }
        if ( params.containsKey("areaId") && !StringUtils.isEmpty(params.get("areaId")) ){
            sb.append(" and so.area_id =  #{areaId}");
        }

        return sb.toString();
    }

    public String insertBathOfficeWaste(@Param("id") long id, @Param("wastes") List<Waste> wastes){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `guguanjia`.`sys_office_waste`(`waste_id`, `office_id`, `del_flag`, `create_date`, `update_date`, `create_by`) VALUES ");
        for (int i = 0; i < wastes.size(); i++) {
            sb.append("(");
            sb.append("#{wastes["+i+"].id},#{id},'0',now(),now(),null");
            sb.append("),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();

    }
}
