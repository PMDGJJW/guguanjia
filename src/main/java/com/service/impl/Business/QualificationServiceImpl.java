package com.service.impl.Business;

import com.dao.QualificationMapper;
import com.entity.Qualification;
import com.entity.QualificationCongition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.QualificationService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/13 18:02
 * @Description
 */
@Service
@Transactional
public class QualificationServiceImpl extends BaseImpl<Qualification> implements QualificationService {

    @Autowired
    QualificationMapper qualificationMapper;

    @Override
    public PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCongition congition) {

        PageHelper.startPage(pageNum, pageSize);
        List<Qualification> list = qualificationMapper.selectConditon(congition);
        return new PageInfo<Qualification>(list);

    }
}
