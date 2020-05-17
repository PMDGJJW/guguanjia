package com.dao;

import com.entity.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser> {
    //动态SQL查询用户
    @SelectProvider(value = SysUserSqlProvider.class,method = "selectByCondition")
    List<SysUser> selectByCondition(Map<String,Object> params);

    @Select("select " +
            " su.id, " +
            "su.company_id, " +
            "su.office_id, " +
            "su.username, " +
            "su.`no`, " +
            "su.`name`, " +
            "su.email, " +
            "su.phone, " +
            "su.mobile, " +
            "su.user_type, " +
            "su.device_code, " +
            "su.login_ip, " +
            "su.login_date, " +
            "su.create_by, " +
            "su.create_date, " +
            "su.update_by, " +
            "su.update_date, " +
            "su.remarks, " +
            "su.del_flag, " +
            "su.`status`, " +
            "su.head_picture " +
            "from " +
            " sys_user_role sur,sys_user su " +
            "where " +
            "sur.del_flag=0 " +
            "and " +
            "su.del_flag=0 " +
            "and " +
            " sur.role_id=#{rid} " +
            "and " +
            " sur.user_id=su.id")
    List<SysUser> selectByRid(long rid);

    @Select("select " +
            " * " +
            "from " +
            " sys_user " +
            "where " +
            " office_id=#{oid} " +
            "and " +
            " id  " +
            "not in " +
            "( " +
            "select " +
            " sur.user_id " +
            "from " +
            " sys_role sr,sys_user_role sur " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.id=sur.role_id " +
            ")")
    List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid")long oid);
}
