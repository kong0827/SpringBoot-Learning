package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiangjin.kong
 * @date 2021/3/25 10:34
 */
@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private String address;

    private int database = 0;

    private String password;

    private int timeout;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 单机
     */
    private RedisSingleProperties single;

    /**
     * 哨兵
     */
    private RedissonSentinelProperties sentinel;

    /**
     * 主从
     */
    private RedissonMasterSlaveProperties masterSlave;

    /**
     * 集群
     */
    private RedissonClusterProperties cluster;


}
