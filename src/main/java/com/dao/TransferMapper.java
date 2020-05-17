package com.dao;

import com.entity.Transfer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransferMapper extends Mapper<Transfer> {
    @Select("select tr.*,su.username `name` , su.phone phone from " +
            "transfer tr,sys_user su " +
            "where  " +
            "tr.work_order_id=#{id} " +
            "and " +
            "tr.oprate_user_id = su.id " +
            "ORDER BY " +
            "tr.update_date desc")
    List<Transfer> list(Long id);

}
