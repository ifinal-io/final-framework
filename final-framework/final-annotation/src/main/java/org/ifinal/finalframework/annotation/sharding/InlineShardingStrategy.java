package org.ifinal.finalframework.annotation.sharding;

import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @see org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@Repeatable(InlineShardingStrategy.ShardingStrategies.class)
@ShardingStrategy(value = ShardingType.INLINE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InlineShardingStrategy {

    ShardingScope scope();

    String[] columns();

    @Property("algorithm-expression")
    String expression();

    @Property("allow-range-query-with-inline-sharding")
    boolean allowRangeQuery() default false;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {
        InlineShardingStrategy[] value();
    }

}
