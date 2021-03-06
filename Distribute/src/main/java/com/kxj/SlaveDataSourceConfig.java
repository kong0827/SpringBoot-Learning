package com.kxj;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2021/1/25 18:04
 * @desc 从数据库配置数据源
 */
@Configuration
public class SlaveDataSourceConfig {

    /**
     * 配置数据库配置
     * 扫描spring.datasource.primary开头的配置信息
     *
     * @return 数据源配置
     */
    @Bean(name = "slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * 获取主库数据源对象
     * @param dataSourceProperties
     * @return
     */
    @Bean(name = "slaveDataSource")
    public DataSource dataSource(@Qualifier("slaveDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为slaveDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "slaveJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("slaveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
