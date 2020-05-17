package com.service.impl.Business;

import com.dao.StatuteMapper;
import com.entity.Statute;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.StatuteService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/12 21:08
 * @Description
 */
@Service
@Transactional
public class StatusServiceImpl extends BaseImpl<Statute> implements StatuteService {
    @Autowired
    StatuteMapper statuteMapper;

    @Override
    public PageInfo<Statute> selectPage(int pageNum, int pageSize, Integer type) {
        Statute statute = new Statute();
        statute.setDelFlag("0");
        if (type!=0){
            statute.setType(type);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Statute> list = statuteMapper.select(statute);
        return new PageInfo<Statute>(list);
    }
}
