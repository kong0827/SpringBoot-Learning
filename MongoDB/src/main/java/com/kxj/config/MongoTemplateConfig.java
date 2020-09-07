package com.kxj.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * mongodb 配置类
 * @author xiangjin.kong
 * @date 2020/9/7 14:02
 */

@Configuration
public class MongoTemplateConfig {

    /**
     * 去除 `_class` 字段
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
        MongoTypeMapper mongoTypeMapper = new DefaultMongoTypeMapper(null);
        mappingConverter.setTypeMapper(mongoTypeMapper);
        return mappingConverter;
    }

//    @Bean
//    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
//                                       MongoConverter converter) {
//        return new MongoTemplate(mongoDbFactory, converter);
//    }

}
