package com.service.impl.Business;

import com.dao.AppVersionMapper;
import com.entity.AppVersion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.ApperVersionService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/9 19:33
 * @Description APP管理实现类
 */
@Service
@Transactional
public class ApperVersionServiceImpl extends BaseImpl<AppVersion> implements ApperVersionService {
    @Autowired
    AppVersionMapper appVersionMapper;
    //分页查询
    @Override
    public PageInfo<AppVersion> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);//开启分页拦截
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> appVersions = appVersionMapper.select(appVersion);
        return new PageInfo<AppVersion>(appVersions);
    }
}
