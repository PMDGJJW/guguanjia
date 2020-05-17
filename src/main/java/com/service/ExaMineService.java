package com.service;

import com.entity.Examine;
import com.github.pagehelper.PageInfo;

import java.util.Map;
/**
 * @Description 资质审核
 * @author jian j w
 * @date 2020/4/15
 */
public interface ExaMineService extends BaseService<Examine> {

    PageInfo<Examine> pageInfo(int pageNum, int pageSize, Map <String,Object> params);

}
