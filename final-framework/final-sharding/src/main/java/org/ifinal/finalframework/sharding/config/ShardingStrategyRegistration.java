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
public class ShardingStrategyRegistration {
    private final ShardingType type;
    private final String name;
    private final String column;
    private final Properties properties;

    public ShardingStrategyRegistration(ShardingType type, String name, String column, Properties properties) {
        this.type = type;
        this.name = name;
        this.column = column;
        this.properties = properties;
    }
}
