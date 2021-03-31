package config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

/**
 * @author xiangjin.kong
 * @date 2021/3/25 10:32
 */
@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@EnableAutoConfiguration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private static RedissonProperties redissonProperties;

    /**
     * 单机
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    public static RedissonClient singleRedissonClient() {
//        config.useMasterSlaveConnection().setMasterAddress("127.0.0.1:6379").addSlaveAddress("127.0.0.1:6389").addSlaveAddress("127.0.0.1:6399");
//        config.useSentinelConnection().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379");
//        config.useClusterServers().addNodeAddress("127.0.0.1:7000");
        Config config = new Config();
        config.useSingleServer().setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        return Redisson.create(config);
    }

    /**
     * 哨兵
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    public static RedissonClient sentinelRedissonClient() {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
        return Redisson.create(config);
    }

    /**
     * 集群
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.nodes")
    public static RedissonClient clusterRedissonClient() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress(redissonProperties.getNodeAddress());
        return Redisson.create(config);
    }
}
