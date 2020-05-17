package com.dao;

import com.entity.SysArea;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 区域管理服务层接口
 * @author jian j w
 * @date 2020/4/18
 */

public interface SysAreaMapper extends Mapper<SysArea> {

    @SelectProvider(value = SysAreaSqlProvider.class, method = "selectByCondition")
    List<SysArea> selectByCondition(Map<String, Object> params);

    @Update("update sys_area set parent_ids=REPLACE(parent_ids,#{oldParentIds},#{parentIds}) " +
            "where parent_ids like  concat('%,',#{id},',%') ")
    int updateParentIds(SysArea sysArea);

    @InsertProvider(type = SysAreaSqlProvider.class,method = "doUpload")
    int insertBatch(@Param("sysAreas") List<SysArea>sysAreas);
}
