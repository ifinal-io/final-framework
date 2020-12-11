package org.ifinal.finalframework.annotation.sharding;

import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@ShardingStrategy(strategy = ShardingStrategy.Strategy.CLASS_BASED, type = ShardingStrategy.Algorithm.CLASS_BASED)
@Repeatable(ClassBasedShardingStrategy.ShardingStrategies.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassBasedShardingStrategy {

    ShardingStrategy.Scope scope();

    @Property(Property.CLASS_BASED_STRATEGY)
    ShardingStrategy.Strategy strategy();

    @Property(Property.CLASS_BASED_ALGORITHM_CLASS_NAME)
    Class<? extends ShardingAlgorithm> algorithm();


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {
        ClassBasedShardingStrategy[] value();
    }
}
