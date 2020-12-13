package org.ifinal.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

/**
 * @author likly
 * @version 1.0.0
 * @see ShardingStrategyConfiguration
 * @see StandardShardingStrategyConfiguration
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface ShardingStrategy {

    Strategy strategy();

    String type();

    /**
     * @author likly
     * @version 1.0.0
     * @see StandardShardingStrategyConfiguration
     * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration
     * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration
     * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.NoneShardingStrategyConfiguration
     * @since 1.0.0
     */
    enum Strategy {
        STANDARD, COMPLEX, HINT, CLASS_BASED
    }

    /**
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    enum Scope {
        DATABASE, TABLE
    }

    class Algorithm {

        public static final String INLINE = "INLINE";

        public static final String HINT_INLINE = "HINT_INLINE";

        public static final String COMPLEX_INLINE = "COMPLEX_INLINE";

        public static final String INTERVAL = "INTERVAL";

        public static final String CLASS_BASED = "CLASS_BASED";

        private Algorithm() {
        }

    }

}
