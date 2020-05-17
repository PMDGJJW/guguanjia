package com.service.impl.Business;

import com.dao.DetailMapper;
import com.entity.Detail;
import com.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/15 19:51
 * @Description
 */
@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    DetailMapper detailMapper;

    @Override
    public List<Detail> selectByDetail(Long id) {
        return detailMapper.selectByDetail(id);
    }
}
