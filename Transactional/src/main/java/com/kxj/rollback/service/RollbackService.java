package com.kxj.rollback.service;

/**
 * @author xiangjin.kong
 * @date 2021/12/15 17:48
 */
public interface RollbackService {

    boolean aTransactional() throws Exception;

}
