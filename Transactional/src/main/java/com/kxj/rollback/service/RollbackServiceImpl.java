package com.kxj.rollback.service;

import com.kxj.entity.User;
import com.kxj.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author xiangjin.kong
 * @date 2021/12/15 17:25
 */
@Slf4j
@Service
public class RollbackServiceImpl implements RollbackService {

    @Resource
    private UserManager userManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void aTransactional() {
        try {
            log.info(TransactionSynchronizationManager.getCurrentTransactionName());
            userManager.insert11(new User(null, "33"));
            bTransactional();
        } catch (Exception e) {
            log.error("捕获异常....");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bTransactional() {
        log.info(TransactionSynchronizationManager.getCurrentTransactionName());
        userManager.insert11(new User(null, "66"));
        System.out.println(1 / 0);
    }


}
