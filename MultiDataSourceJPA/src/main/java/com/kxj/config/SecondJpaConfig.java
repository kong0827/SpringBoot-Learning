package com.kxj.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 14:39
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // repository包名
        basePackages = SecondJpaConfig.REPOSITORY_PACKAGE,
        // 实体管理类名称
        entityManagerFactoryRef = "secondEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "secondTransactionManager")
public class SecondJpaConfig {

    static final String REPOSITORY_PACKAGE = "com.kxj.repository.second";
    private static final String ENTITY_PACKAGE = "com.kxj.entity.second";

    /**
     * JPA的配置信息
     * @return
     */
    @Bean(name = "secondJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.second")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean("secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("secondDataSource") DataSource dataSource,
                                                                       @Qualifier("secondJpaProperties") JpaProperties jpaProperties,
                                                                       EntityManagerFactoryBuilder factoryBuilder) {
        return factoryBuilder
                // 设置数据源
                .dataSource(dataSource)
                // 设置实体包名
                .packages(ENTITY_PACKAGE)
                // 设置jpa配置
                .properties(jpaProperties().getProperties())
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("secondPersistentUnit")
                .build();
    }

    /**
     * 获取实体管理对象
     * @param factory
     * @return
     */
    @Bean("secondEntityManager")
    public EntityManager entityManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory
     * @return
     */
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
