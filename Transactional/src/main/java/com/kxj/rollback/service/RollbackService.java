package com.kxj.rollback.service;

/**
 * @author xiangjin.kong
 * @date 2021/12/15 17:48
 */
public interface RollbackService {

    void aTransactional() throws Exception;

    void bTransactional() throws Exception;
}
