package org.ifinal.finalframework.annotation.sharding;

import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@Repeatable(IntervalShardingStrategy.ShardingStrategies.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ShardingStrategy(value = ShardingType.INTERVAL)
public @interface IntervalShardingStrategy {

    ShardingScope scope() default ShardingScope.TABLE;

    @Property("datetime-pattern")
    String pattern();

    @Property("datetime-lower")
    String lower();

    @Property("datetime-upper")
    String upper();

    @Property("sharding-suffix-pattern")
    String suffix();

    @Property("datetime-interval-amount")
    int interval() default 1;

    @Property("datetime-interval-unit")
    ChronoUnit unit() default ChronoUnit.DAYS;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {
        IntervalShardingStrategy[] value();
    }

}
