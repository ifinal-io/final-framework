/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ifinalframework.sharding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

/**
 * @author iimik
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
     * @author iimik
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
     * @author iimik
     * @version 1.0.0
     * @since 1.0.0
     */
    enum Scope {
        DATABASE, TABLE
    }

    /**
     * Sharding Algorithm Type
     */
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
