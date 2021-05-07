package config;



import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    RedissonProperties redisProperties;


    /**
     * 单机模式 redisson 客户端
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "single")
    RedissonClient redissonSingle() {
        Config config = new Config();
        String node = redisProperties.getSingle().getAddress();
        node = node.startsWith("redis://") ? node : "redis://" + node;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node)
                .setTimeout(redisProperties.getPool().getConnTimeout())
                .setConnectionPoolSize(redisProperties.getPool().getSize())
                .setConnectionMinimumIdleSize(redisProperties.getPool().getMinIdle());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }


    /**
     * 集群模式的 redisson 客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "cluster")
    RedissonClient redissonCluster() {
        System.out.println("cluster redisProperties:" + redisProperties.getCluster());

        Config config = new Config();
        String[] nodes = redisProperties.getCluster().getNodes().split(",");
        List<String> newNodes = new ArrayList(nodes.length);
        Arrays.stream(nodes).forEach((index) -> newNodes.add(
                index.startsWith("redis://") ? index : "redis://" + index));

        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(newNodes.toArray(new String[0]))
                .setScanInterval(
                        redisProperties.getCluster().getScanInterval())
                .setIdleConnectionTimeout(
                        redisProperties.getPool().getSoTimeout())
                .setConnectTimeout(
                        redisProperties.getPool().getConnTimeout())
                .setRetryAttempts(
                        redisProperties.getCluster().getRetryAttempts())
                .setRetryInterval(
                        redisProperties.getCluster().getRetryInterval())
                .setMasterConnectionPoolSize(redisProperties.getCluster()
                        .getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redisProperties.getCluster()
                        .getSlaveConnectionPoolSize())
                .setTimeout(redisProperties.getTimeout());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 哨兵模式 redisson 客户端
     * @return
     */

    @Bean
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "sentinel")
    RedissonClient redissonSentinel() {
        System.out.println("sentinel redisProperties:" + redisProperties.getSentinel());
        Config config = new Config();
        String[] nodes = redisProperties.getSentinel().getNodes().split(",");
        List<String> newNodes = new ArrayList(nodes.length);
        Arrays.stream(nodes).forEach((index) -> newNodes.add(
                index.startsWith("redis://") ? index : "redis://" + index));

        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(newNodes.toArray(new String[0]))
                .setMasterName(redisProperties.getSentinel().getMaster())
                .setReadMode(ReadMode.SLAVE)
                .setTimeout(redisProperties.getTimeout())
                .setMasterConnectionPoolSize(redisProperties.getPool().getSize())
                .setSlaveConnectionPoolSize(redisProperties.getPool().getSize());

        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 主从
     * @return
     */
    RedissonClient redissonClientMasterSalve() {
        Config config = new Config();
        try {
            String address = redisProperties.getMasterSlave().getMasterAddress();
            String password = redisProperties.getPassword();
            int database = redisProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            /**设置主节点ip*/
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            /**设置从节点，移除第一个节点，默认第一个为主节点*/
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrToken);
            }
            slaveList.remove(0);

            config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Redisson.create(config);
    }

    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new DistributedLocker();
        locker.setRedissonClient(redissonClient);
        return locker;
    }
}
