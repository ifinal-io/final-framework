package org.ifinal.finalframework.sharding.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.ifinal.finalframework.sharding.annotation.ShardingType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingAlgorithmRegistry {

    @Getter(AccessLevel.PACKAGE)
    private final Collection<ShardingAlgorithmRegistration> shardingAlgorithms = new ArrayList<>();

    public ShardingAlgorithmRegistry addShardingAlgorithm(String name, ShardingType type, Properties properties) {
        shardingAlgorithms.add(new ShardingAlgorithmRegistration(type, name, properties));
        return this;
    }


}

