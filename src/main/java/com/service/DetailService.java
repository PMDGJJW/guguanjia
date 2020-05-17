package com.service;

import com.entity.Detail;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/15 19:50
 * @Description
 */
public interface DetailService {

    List<Detail> selectByDetail(Long id);

}
