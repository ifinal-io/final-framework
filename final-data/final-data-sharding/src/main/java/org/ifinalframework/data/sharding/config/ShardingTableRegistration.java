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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter(AccessLevel.PACKAGE)
@ToString
public class ShardingTableRegistration {

    private final String logicTable;

    private final String actualDataNodes;

    private ShardingStrategyRegistration databaseShardingStrategy;

    private ShardingStrategyRegistration tableShardingStrategy;

    public ShardingTableRegistration(final String logicTable) {
        this.logicTable = logicTable;
        this.actualDataNodes = null;
    }

    public ShardingTableRegistration(final String logicTable, final String... actualDataNodes) {
        this.logicTable = logicTable;
        this.actualDataNodes = String.join(",", actualDataNodes);
    }

    public ShardingTableRegistration setDatabaseShardingStrategy(final ShardingStrategyRegistration shardingStrategy) {
        this.databaseShardingStrategy = shardingStrategy;
        return this;
    }

    public ShardingTableRegistration setTableShardingStrategy(final ShardingStrategyRegistration shardingStrategy) {
        this.tableShardingStrategy = shardingStrategy;
        return this;
    }

}
