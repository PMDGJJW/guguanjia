package com.dao;

import com.entity.SysResource;
import com.entity.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {
    @SelectProvider(type = SysRoleSqlProvider.class,method = "selectByCondition")
    List<SysRole> selectByCondition(Map<String, Object> params);

    /**
     * 删除已选人员
     * 根据角色id和用户的ids删除sys_user_role记录
     */
    @DeleteProvider(type = SysRoleSqlProvider.class,method="deleteBatch")
    int deleteBatch(@Param("rid") long rid,@Param("ids") long[] ids);

    @InsertProvider(type = SysRoleSqlProvider.class,method = "insertBatch")
    int insertBatch(@Param("rid")long rid,@Param("cids") List<Long> cids);

    @Delete("delete from sys_role_resource where role_id=#{rid}")
    int  updateByDeleteRole(@Param("rid") long rid);

    @Delete("delete from sys_role_office where role_id=#{rid}")
    int  updateByDeleteOffice(@Param("rid") long rid);

    @UpdateProvider(type = SysRoleSqlProvider.class,method = "updateRole")
    int updateRole(@Param("rid") long rid,@Param("resources") Long[]resources);


    @UpdateProvider(type =SysRoleSqlProvider.class,method ="updateRoleOffice")
    int updateRoleOffice(@Param("rid") long rid,@Param("officeId") Long[]officeId);
}
