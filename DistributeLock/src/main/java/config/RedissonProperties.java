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


}
