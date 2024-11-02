/*
 * Copyright 2020-2021 the original author or authors.
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
 */

package org.ifinalframework.sharding.annotation;

import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;

import java.lang.annotation.*;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
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

    /**
     * ShardingStrategies.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {

        ClassBasedShardingStrategy[] value();

    }

}
