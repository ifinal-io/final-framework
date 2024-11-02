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

import org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm;
import org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm;
import org.apache.shardingsphere.sharding.algorithm.sharding.mod.HashModShardingAlgorithm;
import org.apache.shardingsphere.sharding.algorithm.sharding.mod.ModShardingAlgorithm;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ShardingAlgorithmType {
    /**
     * @see InlineShardingAlgorithm#getType()
     */
    INLINE,
    /**
     * @see ModShardingAlgorithm#getType()
     */
    MOD,
    /**
     * @see HashModShardingAlgorithm#getType()
     */
    HASH_MOD,

    /**
     * @see IntervalShardingAlgorithm#getType()
     */
    INTERVAL,

    HINT_INLINE,

    COMPLEX_INLINE,

    CLASS_BASED
}
