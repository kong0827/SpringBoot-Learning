package com.kxj.controller;

import com.kxj.service.RollbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangjin.kong
 * @date 2021/12/16 11:15
 */
@RestController
public class TestController {

    @Autowired
    RollbackService rollbackService;

    @GetMapping
    public void test()  {
        rollbackService.aTransactional();
    }

}
