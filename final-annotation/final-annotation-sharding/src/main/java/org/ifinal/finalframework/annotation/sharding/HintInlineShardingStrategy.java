package org.ifinal.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.auto.service.annotation.AutoService;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@Repeatable(HintInlineShardingStrategy.ShardingStrategies.class)
@ShardingStrategy(strategy = ShardingStrategy.Strategy.HINT, type = ShardingStrategy.Algorithm.HINT_INLINE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HintInlineShardingStrategy {

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

        HintInlineShardingStrategy[] value();

    }

}
