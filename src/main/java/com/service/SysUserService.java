package com.service;

import com.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/24 20:17
 * @Description
 */
public interface SysUserService extends BaseService<SysUser> {

    PageInfo<SysUser> selectByCondition(int pageNum, int pageSize, Map<String, Object> params);

    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(long rid, long oid);

}
