/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.sharding.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingAlgorithmRegistry {

    @Getter(AccessLevel.PACKAGE)
    private final Collection<ShardingAlgorithmRegistration> shardingAlgorithms = new ArrayList<>();

    public ShardingAlgorithmRegistry addShardingAlgorithm(final String name, final String type,
                                                          final Properties properties) {

        shardingAlgorithms.add(new ShardingAlgorithmRegistration(type, name, properties));
        return this;
    }

}

