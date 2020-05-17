package com.service;

import com.entity.Office;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/21 18:20
 * @Description
 */
public interface MechanismService extends BaseService<Office> {

    PageInfo<Office> selectPage(int pageNum, int pageSize, Map<String,Object> params);

    List<Office> officeZtree(Map<String,Object>params);



}
