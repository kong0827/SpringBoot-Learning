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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 16:41
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.kxj.dao.salve", sqlSessionTemplateRef  = "salveSessionTemplate")
public class SalveDataSourceConfig {
    @Bean(name = "salveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.salve")
    //  @Primary
    public DataSource salveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "salveSessionFactory")
    //  @Primary
    public SqlSessionFactory salveSessionFactory(@Qualifier("salveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置mybatis的XML的位置
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResource("classpath*:/mapper/salve/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "SalveTransactionManager")
    //   @Primary
    public DataSourceTransactionManager salveTransactionManager(@Qualifier("salveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "salveSessionTemplate")
    //   @Primary
    public SqlSessionTemplate salveSessionTemplate(@Qualifier("salveSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}