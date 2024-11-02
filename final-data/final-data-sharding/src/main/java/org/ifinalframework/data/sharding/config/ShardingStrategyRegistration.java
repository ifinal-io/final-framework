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

import org.ifinalframework.sharding.annotation.ShardingStrategy;

import java.util.Properties;

import lombok.Getter;
import lombok.ToString;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString
public class ShardingStrategyRegistration {

    private final ShardingStrategy.Strategy strategy;

    private final String type;

    private final String name;

    private final String[] columns;

    private final Properties properties;

    public ShardingStrategyRegistration(final ShardingStrategy.Strategy strategy, final String type, final String name,
                                        final String[] column,
                                        final Properties properties) {

        this.strategy = strategy;
        this.type = type;
        this.name = name;
        this.columns = column;
        this.properties = properties;
    }

}
