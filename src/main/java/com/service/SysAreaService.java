package com.service;

import com.entity.SysArea;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/18 15:55
 * @Description
 */
public interface SysAreaService extends BaseService<SysArea> {

    PageInfo<SysArea> selectPage(int pageNum, int pageSize, Map<String, Object> params);
    void downloadExcel(OutputStream ops);

    int upload(InputStream is);
}
