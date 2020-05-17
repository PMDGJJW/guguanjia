package com.service;

import com.entity.Transfer;

import java.util.List;

public interface TransferService {

    List<Transfer> selectByTransfer(Long id);
}
