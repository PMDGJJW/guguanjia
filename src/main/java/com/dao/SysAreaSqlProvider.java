package com.dao;

import com.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/18 15:36
 * @Description 区域管理动态SQL
 */
public class SysAreaSqlProvider {

    //根据姓名或者区域id查询
    public String selectByCondition(Map<String,Object>params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " sa.* ,sr.name parent_name,sa.parent_ids old_parent_ids " +
                "FROM " +
                " sys_area sa " +
                " left join  " +
                " sys_area sr " +
                "on  " +
                "sa.parent_id = sr.id " +
                "WHERE " +
                "sa.del_flag=0 ");
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))){
            sb.append(" and  " +
                    "sa.name like concat('%',#{name},'%')");
        }
        if (params.containsKey("id") && !StringUtils.isEmpty("id")){
            sb.append(" and " +
                    "sa.parent_ids like concat('%,',#{id},'%,') ");
        }
        return sb.toString();
    }

    public String doUpload(@Param("sysAreas") List<SysArea> sysAreas){
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_area");
        sql.INTO_COLUMNS("parent_id", "parent_ids", "code", "name", "type", "create_by", "create_date", "update_by", "update_date", "remarks", "del_flag", "icon");
        for (int i = 0; i < sysAreas.size(); i++) {
            sql.INTO_VALUES("#{sysAreas["+i+"].parentId},#{sysAreas["+i+"].parentIds},#{sysAreas["+i+"].code},#{sysAreas["+i+"].name},#{sysAreas["+i+"].type},#{sysAreas["+i+"].createBy},#{sysAreas["+i+"].createDate},#{sysAreas["+i+"].updateBy},#{sysAreas["+i+"].updateDate},#{sysAreas["+i+"].remarks},#{sysAreas["+i+"].delFlag},#{sysAreas["+i+"].icon}");
            sql.ADD_ROW();
        }
        return sql.toString();
    }
}
