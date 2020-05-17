package com.service.impl.Business;

import com.dao.WorkOrderMapper;
import com.entity.WorkOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.WorkOrderService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/15 13:21
 * @Description 电子台账实现类
 */
@Service
@Transactional
public class WorkOrderServiceImpl extends BaseImpl<WorkOrder> implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Override
    public PageInfo<WorkOrder> selectPage(int pageNum, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<WorkOrder> list = workOrderMapper.selectByCondition(params);
        return new PageInfo<WorkOrder>(list);
    }
}
