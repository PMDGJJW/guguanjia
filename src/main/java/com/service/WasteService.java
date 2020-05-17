package com.service;

import com.entity.Waste;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/21 22:59
 * @Description
 */
public interface WasteService extends BaseService<Waste> {

    List<Waste> selectByOid(long oid);
}
