package org.finalframework.boot.autoconfigure.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:28:37
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = ZookeeperProperties.PREFIX)
public class ZookeeperProperties {
    static final String PREFIX = "final.zookeeper";

    private String address;
    private Integer timeout;
}
