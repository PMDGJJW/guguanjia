package com.service.impl.Business;

import com.dao.DemandMapper;
import com.entity.Demand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.DemandService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/11 12:51
 * @Description 服务需求实现类
 */
@Service
@Transactional
public class DemandServiceImpl extends BaseImpl<Demand> implements DemandService {

    @Autowired
    DemandMapper demandMapper;
    //分页查询
    @Override
    public PageInfo<Demand> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Demand demand = new Demand();
        demand.setDelFlag("0");
        List<Demand> demands = demandMapper.select(demand);
        return new PageInfo<Demand>(demands);
    }
}
