package com.dao;

import com.entity.WorkOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {

    @SelectProvider(value = WorkOrderSqlProvider.class,method = "selectByCondition")
    List<WorkOrder>selectByCondition(Map<String,Object>params);
}
