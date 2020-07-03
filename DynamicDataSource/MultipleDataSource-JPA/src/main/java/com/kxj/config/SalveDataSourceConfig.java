package com.kxj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiangjin.kong
 * @date 2020/7/3 13:55
 * @desc
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySalve",
        transactionManagerRef = "transactionManagerSalve",
        basePackages = {"com.kxj.dao.salve"}) //设置Repository所在位置
public class SalveDataSourceConfig {

    @Resource
    @Qualifier("salveDataSource")
    private DataSource salveDataSource;


    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerSalve")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactorySalve(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactorySalve")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySalve (EntityManagerFactoryBuilder builder) {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setGenerateDdl(true);
        return builder
                .dataSource(salveDataSource)
                .packages("com.kxj.entity") //设置实体类所在位置
                .persistenceUnit("salvePersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Bean(name = "transactionManagerSalve")
    public PlatformTransactionManager transactionManagerSalve(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySalve(builder).getObject());
    }

}