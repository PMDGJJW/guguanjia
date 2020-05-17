package com.dao;

import com.entity.Detail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DetailMapper extends Mapper<Detail> {
    @Select("select de.*,wt.`name` waste_type_name,wt.`code` waste_type_code ,wa.`code` waste_code from  " +
            "detail de ,waste_type wt ,waste wa " +
            "WHERE de.work_order_id=#{id} " +
            "and de.waste_id = wa.id " +
            "and de.waste_type_id = wt.id " +
            "and de.del_flag=0 " +
            "and wt.del_flag=0 " +
            "and wa.del_flag=0")
    List<Detail> selectByDetail(Long id);
}
