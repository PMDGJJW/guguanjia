package com.service;

import com.entity.SysResource;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/27 19:45
 * @Description
 */
public interface SysResourceService extends BaseService<SysResource> {

    List<SysResource> selectCheck(Long rid);

    List<SysResource> selectByUid(Long uid);
}
