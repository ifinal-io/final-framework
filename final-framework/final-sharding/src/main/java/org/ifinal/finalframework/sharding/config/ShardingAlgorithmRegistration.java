package org.ifinal.finalframework.sharding.config;

import lombok.Getter;
import org.ifinal.finalframework.sharding.annotation.ShardingType;

import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class ShardingAlgorithmRegistration {
    private final ShardingType type;
    private final String name;
    private final Properties properties;

    public ShardingAlgorithmRegistration(ShardingType type, String name, Properties properties) {
        this.type = type;
        this.name = name;
        this.properties = properties;
    }
}
