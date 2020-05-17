package com.service;

import com.entity.SysLog;
import com.github.pagehelper.PageInfo;

/**
 * @auth jian j w
 * @date 2020/5/2 22:23
 * @Description
 */
public interface SysLogService extends BaseService<SysLog> {

    PageInfo<SysLog>selectPage(int pageNum,int pageSize,SysLog params);
}
