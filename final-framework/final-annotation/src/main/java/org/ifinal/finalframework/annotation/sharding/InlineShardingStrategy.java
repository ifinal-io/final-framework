package org.ifinal.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.auto.annotation.AutoService;

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
@ShardingStrategy(strategy = ShardingStrategy.Strategy.STANDARD, type = ShardingStrategy.Algorithm.INLINE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InlineShardingStrategy {

    ShardingStrategy.Scope scope();

    @Property(Property.INLINE_SHARING_COLUMNS)
    String[] columns();

    @Property(Property.INLINE_ALGORITHM_EXPRESSION)
    String expression();

    @Property(Property.INLINE_ALLOW_RANGE_QUERY)
    boolean allowRangeQuery() default false;

    /**
     * ShardingStrategies.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {

        InlineShardingStrategy[] value();

    }

}
