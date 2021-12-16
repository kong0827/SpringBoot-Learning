package com.kxj.service;

import com.kxj.entity.UserEntity;
import com.kxj.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author xiangjin.kong
 * @date 2021/12/15 17:25
 */
@Slf4j
@Service
public class RollbackServiceImpl implements RollbackService {

    @Autowired
    UserRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void aTransactional() {
        try {
            log.info(TransactionSynchronizationManager.getCurrentTransactionName());
            bTransactional();
        } catch (Exception e) {
            log.error("捕获异常....");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void bTransactional() {
        log.info(TransactionSynchronizationManager.getCurrentTransactionName());
        repository.save(new UserEntity(1, "xxxxxxxxxx"));
    }


}
