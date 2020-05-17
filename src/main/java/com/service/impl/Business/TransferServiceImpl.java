package com.service.impl.Business;

import com.dao.TransferMapper;
import com.entity.Transfer;
import com.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/15 19:48
 * @Description
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferMapper transferMapper;

    @Override
    public List<Transfer> selectByTransfer(Long id) {
        return transferMapper.list(id);
    }
}
