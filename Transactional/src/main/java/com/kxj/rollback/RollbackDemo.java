package com.kxj.rollback;

import com.kxj.rollback.service.RollbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiangjin.kong
 * @date 2021/12/15 17:16
 * @desc
 * Transaction silently rolled back because it has been marked as rollback-only
 * 错误产生的原因及演示
 * 如果A事务中调用另外一个B事务方法，A事务捕获B事务的异常，不抛出，会出现以上异常
 *
 * 原因：两者同使用一个事务，B方法抛出了异常把事务标记为RollbackOnly，A方法执行完准备commit发现事务已经是RollbackOnly则报错
 */
@Slf4j
@RestController
public class RollbackDemo {

    @Resource
    private RollbackService rollbackService;

    @GetMapping("test1")
    public boolean test() throws Exception {
        return rollbackService.aTransactional();
    }



}
