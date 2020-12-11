package org.ifinal.finalframework.sharding.config;

import lombok.Getter;
import org.ifinal.finalframework.annotation.sharding.ShardingStrategy;

import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class ShardingStrategyRegistration {
    private final ShardingStrategy.Strategy strategy;
    private final String type;
    private final String name;
    private final String[] columns;
    private final Properties properties;

    public ShardingStrategyRegistration(ShardingStrategy.Strategy strategy, String type, String name, String[] column, Properties properties) {
        this.strategy = strategy;
        this.type = type;
        this.name = name;
        this.columns = column;
        this.properties = properties;
    }
}
