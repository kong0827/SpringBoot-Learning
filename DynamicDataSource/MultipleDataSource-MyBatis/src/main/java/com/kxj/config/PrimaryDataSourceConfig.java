package com.kxj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 16:13
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.kxj.dao.primary", sqlSessionTemplateRef  = "primarySessionTemplate")
public class PrimaryDataSourceConfig {

    // 默认hakir
//    @Bean(name = "primaryDataSource") //As a bean object and named
//    @ConfigurationProperties(prefix = "spring.datasource.master") //In the configuration file, the prefix of the data source
//    @Primary   //For marking up master data sources, this annotation is not added to any injected files other than master data sources
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    // 配置druid
    @Bean(name = "primaryDataSource") //As a bean object and named
    @ConfigurationProperties(prefix = "spring.datasource.druid.master") //In the configuration file, the prefix of the data source
    @Primary   //For marking up master data sources, this annotation is not added to any injected files other than master data sources
    public DruidDataSource primaryDataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "primarySessionFactory")
    @Primary
    public SqlSessionFactory primarySessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置mybatis的XML的位置
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResource("classpath*:/mapper/master/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primarySessionTemplate")
    @Primary
    public SqlSessionTemplate primarySessionTemplate(@Qualifier("primarySessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}