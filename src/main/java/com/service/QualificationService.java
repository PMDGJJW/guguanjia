package com.service;

import com.entity.Qualification;
import com.entity.QualificationCongition;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QualificationService extends BaseService<Qualification> {

    PageInfo<Qualification> selectPage(int pageNum , int pageSize , QualificationCongition congition);

}
