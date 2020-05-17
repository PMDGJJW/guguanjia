package com.service;

import com.entity.Office;

import java.util.List;

public interface OfficeService extends BaseService<Office> {

    List<Office> selectByRid(long rid);
}
