package com.kxj;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2021/1/25 16:43
 * @desc 主数据源
 */
@Configuration
public class MasterDataSourceConfig {

    @Bean(name = "secondJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.second")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 配置数据库配置
     * 扫描spring.datasource.master开头的配置信息
     *
     * @return 数据源配置
     */
    @Primary
    @Bean(name = "masterDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * 获取主库数据源对象
     * @param dataSourceProperties
     * @return
     */
    @Primary
    @Bean(name = "masterDataSource")
    public DataSource dataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为masterDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Primary
    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("masterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
