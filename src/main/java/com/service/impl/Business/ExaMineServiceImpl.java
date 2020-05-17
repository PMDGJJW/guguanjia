package com.service.impl.Business;

import com.dao.ExamineMapper;
import com.entity.Examine;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.ExaMineService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/14 17:55
 * @Description 资质审核实现类
 */
@Service
@Transactional
public class ExaMineServiceImpl extends BaseImpl<Examine> implements ExaMineService {

    @Autowired
    ExamineMapper examineMapper;
    //分页查询
    @Override
    public PageInfo<Examine> pageInfo(int pageNum, int pageSize, Map<String,Object> params) {
        PageHelper.startPage(pageNum,pageSize);
        List<Examine>list = examineMapper.selectByCondition(params);
        return new PageInfo<Examine>(list);
    }
}
