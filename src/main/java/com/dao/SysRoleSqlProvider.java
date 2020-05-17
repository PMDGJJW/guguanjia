package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class SysRoleSqlProvider {

    /**
     * 根据条件，动态生成sql语句
     * @param params
     * @return
     */
    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select " +
                " sr.*,so.name office_name " +
                "from " +
                " sys_role sr,sys_office so " +
                "where " +
                " sr.del_flag=0 "
        );

        if(params.containsKey("dataScope")&&!StringUtils.isEmpty(params.get("dataScope"))){

            sb.append(" and sr.data_scope=#{dataScope}  ");
        }
        if(params.containsKey("remarks")&&!StringUtils.isEmpty(params.get("remarks"))){
            sb.append(" and sr.remarks like CONCAT('%',#{remarks},'%') ");
        }

        if(params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
            sb.append(" and so.id=#{oid} ");
        }

        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append(" AND sr.name like CONCAT('%',#{name},'%') ");
        }
        sb.append(" and sr.office_id=so.id ");
        return sb.toString();
    }


    public String deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE  from sys_user_role where role_id=#{rid} and user_id in ");
        sb.append("(");
        for (int i = 0; i < ids.length; i++) {
            sb.append("#{ids["+i+"]},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    public String insertBatch(@Param("rid")long rid,@Param("cids") List<Long> cids){
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO `sys_user_role`( `role_id`, `user_id`, `create_by`, `create_date`, " +
                    "`update_by`, `update_date`, `del_flag`) VALUES ");

            for (int i = 0; i < cids.size(); i++) {
                sb.append("(#{rid},#{cids["+i+"]},null,now(),null,now(),0),");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
    }

   public String updateRole(@Param("rid") long rid,@Param("resources") Long[]resources){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT into sys_role_resource (role_id,resource_id,create_date,update_date,del_flag ) VALUES ");
        for (int i = 0; i <resources.length ; i++) {
            sb.append("( #{rid},#{resources["+i+"]},now(),now(),0 ),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String updateRoleOffice(@Param("rid") long rid,@Param("officeId") Long[]officeId){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT into sys_role_office (role_id,office_id,create_date,update_date ,del_flag ) VALUES ");
        for (int i = 0; i <officeId.length ; i++) {
            sb.append("( #{rid},#{officeId["+i+"]},now(),now(),0 ),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
