package org.ifinal.finalframework.sharding.config;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ShardingConfigurer {


    default void addShardingTableRule(@NonNull ShardingTableRuleRegistry registry) {
    }

    default void addBindingTables(@NonNull BindingTableRegistry registry){

    }

    default void addBroadcastTables(@NonNull BroadcastTableRegistry registry){

    }

    default void addShardingAlgorithms(@NonNull ShardingAlgorithmRegistry registry) {
    }

}
