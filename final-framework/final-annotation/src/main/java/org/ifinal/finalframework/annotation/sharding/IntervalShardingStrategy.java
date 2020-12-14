package org.ifinal.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;
import org.ifinal.finalframework.auto.service.annotation.AutoService;

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
@ShardingStrategy(strategy = ShardingStrategy.Strategy.STANDARD, type = ShardingStrategy.Algorithm.INTERVAL)
public @interface IntervalShardingStrategy {

    ShardingStrategy.Scope scope();

    String[] columns();

    @Property(Property.INTERVAL_DATETIME_PATTERN)
    String pattern();

    @Property(Property.INTERVAL_DATETIME_LOWER)
    String lower();

    @Property(Property.INTERVAL_DATETIME_UPPER)
    String upper();

    @Property(Property.INTERVAL_SHARDING_SUFFIX_PATTERN)
    String suffix();

    @Property(Property.INTERVAL_DATETIME_INTERVAL_AMOUNT)
    int interval() default 1;

    @Property(Property.INTERVAL_DATETIME_INTERVAL_UNIT)
    ChronoUnit unit() default ChronoUnit.DAYS;

    /**
     * ShardingStrategies.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {

        IntervalShardingStrategy[] value();

    }

}
