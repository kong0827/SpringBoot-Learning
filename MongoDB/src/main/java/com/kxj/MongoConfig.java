package com.kxj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * mongodb 配置类
 * @author xiangjin.kong
 * @date 2020/9/7 14:02
 */

@Configuration
public class MongoConfig {

    /**
     * 去除 `_class` 字段、
     * mongodb存储的时候会有_class字段
     * @param factory
     * @param context
     * @param conversions
     * @return
     */
    @Bean("converter")
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                       MongoMappingContext context, MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
                context);
        mappingConverter.setCustomConversions(conversions);
        //不设置_class字段
        MongoTypeMapper mongoTypeMapper = new DefaultMongoTypeMapper(null);
        mappingConverter.setTypeMapper(mongoTypeMapper);
        return mappingConverter;
    }

//    @Bean
//    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
//                                       MongoConverter converter) {
//        return new MongoTemplate(mongoDbFactory, converter);
//    }


    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory factory){
        return new MongoTransactionManager(factory);
    }


}
