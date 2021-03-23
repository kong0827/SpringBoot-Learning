//package com.kxj.async;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.async.DeferredResult;
//
//import java.util.concurrent.Callable;
//
///**
// * @author kxj
// * @date 2020/3/12 22:30
// * @desc
// */
//
//@RestController
//@RequestMapping("/async")
//@Slf4j
//public class AsyncController {
//
//    @Autowired
//    private MockQueue mockQueue;
//
//    @Autowired
//    private DeferredResultHolder deferredResultHolder;
//
//    @GetMapping("/order")
//    public Callable<String> order() {
//        log.info("主线程开始执行............");
//
//        Callable<String> result = () -> {
//            log.info("副线程执行");
//            Thread.sleep(1000);
//            log.info("副线程返回");
//            return "success";
//        };
//
//        log.info("主线程返回.......");
//        return result;
//
//    }
//
//    @GetMapping
//    public DeferredResult<String> orders() {
//        log.info("主线程开始");
//
//        // 模拟生成订单号
//        String orderNumber = RandomStringUtils.randomNumeric(8);
//        // 发送订单请求
//        mockQueue.setPlaceOrder(orderNumber);
//
//        DeferredResult<String> result = new DeferredResult<>();
//        deferredResultHolder.getMap().put(orderNumber, result);
//
//        log.info("主线程返回");
//        return result;
//
//    }
//
//}
