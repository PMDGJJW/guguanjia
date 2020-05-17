package com.service;

import com.entity.AppVersion;
import com.github.pagehelper.PageInfo;

/**
 * @auth jian j w
 * @date 2020/4/9 19:21
 * @Description App管理
 */
public interface ApperVersionService extends BaseService<AppVersion>{

    PageInfo<AppVersion> selectPage(int pageNum, int pageSize);

}
