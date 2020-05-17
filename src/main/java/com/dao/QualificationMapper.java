package com.dao;

import com.entity.Qualification;
import com.entity.QualificationCongition;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QualificationMapper extends Mapper<Qualification> {

    @SelectProvider(type = QualificationSqlProvider.class,method = "selectCondition")
    List<Qualification> selectConditon(QualificationCongition congition);
}
