package com.dao;

import com.entity.SysResource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {

    @Select("SELECT " +
            " *  " +
            "FROM " +
            " sys_resource sr, " +
            " sys_role_resource srr  " +
            "WHERE " +
            " srr.role_id = #{rid}  " +
            " AND sr.id = srr.resource_id ")
    List<SysResource> selectCheck(Long rid);

    @Select("select sr.* from  " +
            "sys_resource sr LEFT JOIN " +
            "sys_role_resource srr " +
            "on  " +
            "sr.id=srr.resource_id " +
            "LEFT JOIN sys_user_role sur  " +
            "on sur.role_id = srr.role_id " +
            "where sur.user_id=#{uid} ")
    List<SysResource> selectByUid(Long uid);
}
