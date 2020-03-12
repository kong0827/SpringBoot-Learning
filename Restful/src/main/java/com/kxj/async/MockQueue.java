package com.kxj.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author kxj
 * @date 2020/3/12 23:14
 * @desc 模拟下单
 */
@Component
@Slf4j
public class MockQueue implements Serializable {
    private static final long serialVersionUID = 3844418539989931372L;

    // 下单
    private String placeOrder;

    // 订单完成
    private String completeOrder;

    public MockQueue() {
    }

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            log.info("接到下单请求：" + placeOrder);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕：" + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
