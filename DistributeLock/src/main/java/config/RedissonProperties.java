package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiangjin.kong
 * @date 2021/3/25 10:34
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonProperties {

    private int database = 0;

    /**
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
     */
    private int timeout;

    private String password;

    private String mode;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 单机信息配置
     */
    private RedisSingleProperties single;

    /**
     * 集群 信息配置
     */
    private RedissonClusterProperties cluster;

    /**
     * 哨兵配置
     */
    private RedissonSentinelProperties sentinel;

    /**
     * 主从
     */
    private RedissonMasterSlaveProperties masterSlave;

}
