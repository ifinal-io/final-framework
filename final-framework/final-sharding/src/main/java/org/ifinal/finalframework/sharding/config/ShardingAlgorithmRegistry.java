package org.ifinal.finalframework.sharding.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingAlgorithmRegistry {

    @Getter(AccessLevel.PACKAGE)
    private final Collection<ShardingAlgorithmRegistration> shardingAlgorithms = new ArrayList<>();

    public ShardingAlgorithmRegistry addShardingAlgorithm(final String name, final String type,
        final Properties properties) {

        shardingAlgorithms.add(new ShardingAlgorithmRegistration(type, name, properties));
        return this;
    }

}

