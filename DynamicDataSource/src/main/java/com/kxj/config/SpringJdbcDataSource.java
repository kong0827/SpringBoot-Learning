package com.kxj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/7/1 10:49
 * @desc
 */
@Configuration
public class SpringJdbcDataSource {

    /**
     * DataSourceBuilder自动识别配置文件中的 ，因此我们需要在方法体上加上 @ ConfigurationProperties 注解
     * 使用默认的hikari数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
