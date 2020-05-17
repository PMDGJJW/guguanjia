package com.service.impl.Business;

import com.dao.WasteMapper;
import com.entity.Waste;
import com.service.WasteService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/21 22:59
 * @Description
 */
@Service
@Transactional
public class WasteServiceImpl extends BaseImpl<Waste> implements WasteService {
    @Autowired
    WasteMapper wasteMapper;

    @Override
    public List<Waste> selectByOid(long oid){
        return wasteMapper.selectByOid(oid);
    }
}
