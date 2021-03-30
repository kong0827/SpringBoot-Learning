package config;

import lombok.Data;

/**
 * @author xiangjin.kong
 * @date 2021/3/30 18:50
 */
@Data
public class RedissonSentinelProperties {

    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failMax;

}
