package org.ifinal.finalframework.boot.autoconfigure.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = ZookeeperProperties.PREFIX)
public class ZookeeperProperties {
    static final String PREFIX = "final.zookeeper";

    private String address;
    private Integer timeout;
}
