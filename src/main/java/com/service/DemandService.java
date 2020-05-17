package com.service;

import com.entity.Demand;
import com.github.pagehelper.PageInfo;
/**
 * @Description 服务需求
 * @author jian j w
 * @date 2020/4/15
 */
public interface DemandService extends BaseService<Demand> {

    PageInfo<Demand>selectPage(int pageNum,int pageSize);

}
