package com.dao;

import com.entity.Office;
import com.entity.Waste;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfficeMapper extends Mapper<Office> {

    @SelectProvider(value = OfficeSqlProvider.class,method = "selectOffice")
    List<Office> selectOffice(Map<String,Object> params);

    //根据office的id删除数据
    @Delete("delete from sys_office_waste where office_id=#{oid}")
    int deleteOfficeWaste(long oid);

    //批量插入记录
    @InsertProvider(type = OfficeSqlProvider.class,method = "insertBathOfficeWaste")
    int insertBathOfficeWaste(@Param("id") long id, @Param("wastes") List<Waste> wastes);

    @Select("select sof.* from  " +
            "sys_role_office sro,sys_office sof " +
            "where " +
            "sro.role_id=#{rid} " +
            "and " +
            "sof.del_flag=0 " +
            "and " +
            "sro.office_id=sof.id")
    List<Office> selectByRid(long rid);
}
