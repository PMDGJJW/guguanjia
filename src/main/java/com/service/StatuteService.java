package com.service;

import com.entity.Statute;
import com.github.pagehelper.PageInfo;

public interface StatuteService extends BaseService<Statute> {

    PageInfo<Statute> selectPage(int pageNum,int pageSize,Integer type);

}
