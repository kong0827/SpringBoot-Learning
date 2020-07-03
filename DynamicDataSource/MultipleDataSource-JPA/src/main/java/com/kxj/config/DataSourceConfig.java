package com.kxj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author xiangjin.kong
 * @date 2020/7/3 10:57
 * @desc
 */
@Configuration
public class DataSourceConfig {

    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean("masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DruidDataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 从数据源
     * @return
     */
    @Bean("salveDataSource")
    @Qualifier("salveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.salve")
    public DruidDataSource salveDataSource() {
        return new DruidDataSource();
    }
}
