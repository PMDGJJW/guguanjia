package com.service.impl.Business;

import com.dao.SysResourceMapper;
import com.entity.SysResource;
import com.service.SysResourceService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/27 19:46
 * @Description
 */
@Service
@Transactional
public class SysResourceServiceImpl extends BaseImpl<SysResource> implements SysResourceService {

    @Autowired
    SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> selectCheck(Long rid) {
        return sysResourceMapper.selectCheck(rid);
    }

    @Override
    public List<SysResource> selectByUid(Long uid) {
        return sysResourceMapper.selectByUid(uid);
    }
}
