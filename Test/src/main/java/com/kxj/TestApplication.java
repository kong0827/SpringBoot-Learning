package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 13:55
 */
@EnableAsync
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {

        BigDecimal num1 = new BigDecimal("24");
        BigDecimal num2 = new BigDecimal("24.00");

        int comparisonResult = num1.compareTo(num2);
        SpringApplication.run(TestApplication.class, args);
    }
}
