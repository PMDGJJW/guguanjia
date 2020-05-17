package com.service;

import com.entity.WorkOrder;
import com.github.pagehelper.PageInfo;

import java.util.Map;
/**
 * @Description 电子帐台
 * @author jian j w
 * @date 2020/4/15
 */

public interface WorkOrderService extends BaseService<WorkOrder> {


    PageInfo<WorkOrder>selectPage(int pageNum, int pageSize, Map<String,Object>params);

}
