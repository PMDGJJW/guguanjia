package com.service.impl.Business;

import com.alibaba.excel.EasyExcel;
import com.dao.SysAreaMapper;
import com.entity.SysArea;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.listener.SysAreaListener;
import com.service.SysAreaService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/18 15:56
 * @Description 区域管理实现类
 */
@Service
@Transactional
public class SysAreaServiceImpl extends BaseImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;

    //分页查询
    @Override
    public PageInfo<SysArea> selectPage(int pageNum, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArea> sysAreas = sysAreaMapper.selectByCondition(params);
        return new PageInfo<SysArea>(sysAreas);
    }

    @Override
    public int updateByPrimaryKeySelective(SysArea sysArea) {
        int result = 0;
        result = super.updateByPrimaryKeySelective(sysArea);
        if (!sysArea.getParentId().equals(sysArea.getOldParentIds())){
            result+=sysAreaMapper.updateParentIds(sysArea);
        }
        return result;
    }

    @Override
    public void downloadExcel(OutputStream ops) {
        List<SysArea> list = sysAreaMapper.selectAll();
        EasyExcel.write(ops,SysArea.class).sheet().doWrite(list);
    }

    @Override
    public int upload(InputStream is) {
        int result = 0;
        EasyExcel.read(is, SysArea.class, new SysAreaListener(sysAreaMapper)).sheet().doRead();
        result++;
        return result;
    }
}
