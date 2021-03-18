package org.ifinal.finalframework.sharding.config;

import java.util.Properties;
import lombok.Getter;
import lombok.ToString;
import org.ifinal.finalframework.sharding.annotation.ShardingStrategy;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString
public class ShardingStrategyRegistration {

    private final ShardingStrategy.Strategy strategy;

    private final String type;

    private final String name;

    private final String[] columns;

    private final Properties properties;

    public ShardingStrategyRegistration(final ShardingStrategy.Strategy strategy, final String type, final String name,
        final String[] column,
        final Properties properties) {

        this.strategy = strategy;
        this.type = type;
        this.name = name;
        this.columns = column;
        this.properties = properties;
    }

}
