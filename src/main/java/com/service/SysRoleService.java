package com.service;

import com.entity.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/25 13:42
 * @Description
 */
public interface SysRoleService extends BaseService<SysRole> {

    PageInfo<SysRole> selectByCondition(Map<String, Object> params, int pageNum, int pageSize);


    int deleteBatch(long rid, long[] ids);

    int insertBatch(long rid, List<Long> cids);
}
