package com.service.impl.Business;

import com.dao.OfficeMapper;
import com.entity.Office;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.MechanismService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/21 18:20
 * @Description
 */
@Service
@Transactional
@CacheConfig(cacheNames = "sysOfficeCache")
public class MechanismServiceImpl extends BaseImpl<Office> implements MechanismService {

    @Autowired
    OfficeMapper officeMapper;



    @Cacheable(key = "'sys_office:id:'+#params['id']+':pageNum:'+#pageNum+':pageSize:'+#pageSize+':name:'+#params['name']")
    @Override
    public PageInfo<Office> selectPage(int pageNum, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<Office> list = officeMapper.selectOffice(params);
        return new PageInfo<>(list);
    }

    @Override
    public List<Office> officeZtree(Map<String, Object> params) {
        return officeMapper.selectOffice(params);
    }

    @Override
    public int updateByPrimaryKeySelective(Office sysOffice) {
        int result=0;
        super.updateByPrimaryKeySelective(sysOffice);
        result++;

        if(sysOffice.getWastes()!=null&&sysOffice.getWastes().size()>0){
            //a.删除office的id对应的数据

            officeMapper.deleteOfficeWaste(sysOffice.getId());

            //b.批量插入新的中间表数据
            officeMapper.insertBathOfficeWaste(sysOffice.getId(),sysOffice.getWastes());
        }
        result++;
        return result;
    }


}
