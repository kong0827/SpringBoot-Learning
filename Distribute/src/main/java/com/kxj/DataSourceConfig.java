package com.kxj;

import com.google.common.collect.Maps;
import com.kxj.enums.DataSourceType;
import com.kxj.utils.DataSourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 16:46
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    DataSource masterDataSource;

    @Autowired
    DataSource slaveDataSource;

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<Object, Object> targetDataSource = Maps.newHashMap();
        targetDataSource.put(DataSourceType.MASTER, masterDataSource);
        targetDataSource.put(DataSourceType.SLAVE, slaveDataSource);

        AbstractRoutingDataSource dynamicDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                DataSourceType lookupKey = DataSourceHelper.getDataSourceType();
                if (lookupKey == null) {
                    lookupKey = DataSourceType.MASTER;
                }
                return lookupKey;

            }
        };
        dynamicDataSource.setDefaultTargetDataSource(targetDataSource.get(DataSourceType.MASTER.toString()));
        dynamicDataSource.setTargetDataSources(targetDataSource);
        return dynamicDataSource;
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(dynamicDataSource());
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPackagesToScan("com.kxj.model");
        entityManagerFactory.setJpaProperties(hibernateProperties());
        return entityManagerFactory;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager multiTransactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }

    @Bean(name = "jpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }
}
