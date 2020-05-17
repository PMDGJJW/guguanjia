package com.dao;

import com.entity.SysLog;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface SysLogMapper extends Mapper<SysLog> {

    @SelectProvider(value = SysLogSqlProvider.class,method = "selectByCondition")
    List<SysLog> selectByCondition(SysLog params);
}
