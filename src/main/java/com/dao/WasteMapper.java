package com.dao;

import com.entity.Waste;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WasteMapper extends Mapper<Waste> {

    //根据office的id查询waste信息
    @Select("SELECT " +
            "     wa.*, " +
            "     wt.`code` waste_type_code  " +
            "FROM " +
            "     sys_office_waste sw, " +
            "     waste wa, " +
            "     waste_type wt  " +
            "WHERE " +
            "     sw.office_id = #{oid}  " +
            "     AND sw.del_flag = 0  " +
            "     AND wa.del_flag = 0  " +
            "     AND wt.del_flag = 0  " +
            "     AND sw.waste_id = wa.id  " +
            "     AND wa.parent_id = wt.id")
    List<Waste> selectByOid(long oid);
}
