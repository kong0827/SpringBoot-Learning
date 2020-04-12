package com.kxj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author kxj
 * @date 2020/4/12 20:51
 * @desc
 */
@MapperScan("com.kxj.mapper")
@EnableCaching   // 开启缓存配置
@SpringBootApplication
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
