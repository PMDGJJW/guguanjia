package com.service.impl.Business;

import com.dao.SysLogMapper;
import com.entity.SysLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.SysLogService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/5/2 22:24
 * @Description
 */
@Service
@Transactional
public class SysLogServiceImpl extends BaseImpl<SysLog> implements SysLogService {

    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public PageInfo<SysLog> selectPage(int pageNum, int pageSize, SysLog params) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysLog> sysLogs = sysLogMapper.selectByCondition(params);
        return new PageInfo<SysLog>(sysLogs);
    }

    //单独开启事务，不受其他事务影响
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }
}
