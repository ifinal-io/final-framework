package org.ifinal.finalframework.annotation.sharding;

import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@Repeatable(ComplexInlineShardingStrategy.ShardingStrategies.class)
@ShardingStrategy(strategy = ShardingStrategy.Strategy.COMPLEX, type = ShardingStrategy.Algorithm.COMPLEX_INLINE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexInlineShardingStrategy {

    ShardingStrategy.Scope scope();

    @Property(Property.INLINE_SHARING_COLUMNS)
    String[] columns();

    @Property(Property.INLINE_ALGORITHM_EXPRESSION)
    String expression();

    @Property(Property.INLINE_ALLOW_RANGE_QUERY)
    boolean allowRangeQuery() default false;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {
        ComplexInlineShardingStrategy[] value();
    }

}
