package com.service.impl.Business;

import com.dao.OfficeMapper;
import com.entity.Office;
import com.service.OfficeService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/14 18:03
 * @Description
 */
@Service
@Transactional
public class OfficeServiceImpl extends BaseImpl<Office> implements OfficeService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    OfficeMapper officeMapper;

//    @Override
//    public List<Office> selectAll() {
//        Object o = redisTemplate.opsForValue().get("sys_office:id");
//        List<Office> offices = null;
//        if (o != null) {//如果缓存中有数据
//            offices = (List<Office>) o;
//
//        } else {//没有则需要查询数据库  并放入缓存
//            offices = super.selectAll();
//            redisTemplate.opsForValue().set("sys_office:id", offices);
//        }
//        return offices;
//    }

    @Cacheable(cacheNames = "sysOfficeCache",key = "'sys_office:id'")
    @Override
    public List<Office> selectAll() {
        return super.selectAll();
    }

    @Override
    public List<Office> selectByRid(long rid) {
        return officeMapper.selectByRid(rid);
    }
}
