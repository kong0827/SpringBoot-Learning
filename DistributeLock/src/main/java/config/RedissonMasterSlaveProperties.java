package config;

import lombok.Data;

/**
 * @author xiangjin.kong
 * @date 2021/3/30 18:51
 *
 */
@Data
public class RedissonMasterSlaveProperties {

    private String masterAddress;
    private String slaveAddress;

}
