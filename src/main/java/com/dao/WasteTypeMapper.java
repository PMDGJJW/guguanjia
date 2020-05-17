package com.dao;

import com.entity.WasteType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WasteTypeMapper extends Mapper<WasteType> {

}
