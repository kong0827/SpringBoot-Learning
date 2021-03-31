package config;

import lombok.Data;

/**
 * @author xiangjin.kong
 * @date 2021/3/30 19:35
 */
@Data
public class RedisPoolProperties {
    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;

}
