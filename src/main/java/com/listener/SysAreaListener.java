package com.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dao.SysAreaMapper;
import com.entity.SysArea;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/20 21:24
 * @Description
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    private List<SysArea> list = new ArrayList<>();
    private SysAreaMapper sysAreaMapper;

    public SysAreaListener() {
    }

    public SysAreaListener(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        int i =0;
        if (list.size()==0){
            i = sysAreaMapper.insertBatch(list);
        }
        if (i>0){
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(list.size()>0){
            sysAreaMapper.insertBatch(list);
        }
    }
}
