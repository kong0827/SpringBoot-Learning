package com.kxj.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 16:41
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.kxj.dao.salve", sqlSessionTemplateRef  = "SalveSessionTemplate")
public class SalveDataSourceConfig {
    @Bean(name = "SalveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.salve")
    //  @Primary
    public DataSource SalveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SalveSessionFactory")
    //  @Primary
    public SqlSessionFactory SalveSessionFactory(@Qualifier("SalveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "SalveTransactionManager")
    //   @Primary
    public DataSourceTransactionManager SalveTransactionManager(@Qualifier("SalveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SalveSessionTemplate")
    //   @Primary
    public SqlSessionTemplate SalveSessionTemplate(@Qualifier("SalveSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}