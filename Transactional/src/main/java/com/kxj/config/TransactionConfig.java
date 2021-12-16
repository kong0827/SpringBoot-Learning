package com.kxj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiangjin.kong
 * @date 2021/12/16 11:06
 */
@Configuration
@Lazy
@MapperScan("cn.blockchain.core.mapper")
@EnableTransactionManagement(order = 8)
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager txManager(DruidDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        dataSourceTransactionManager.setGlobalRollbackOnParticipationFailure(false);
        return dataSourceTransactionManager;
    }
}
