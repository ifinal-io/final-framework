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

package org.ifinalframework.boot.autoconfigure.sharding;

import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;

import java.io.Serializable;

/**
 * @author iimik
 * @version 1.0.0
 * @see ShardingTableRuleConfiguration
 * @since 1.0.0
 */
public class TableRuleProperties implements Serializable {

    /**
     * 逻辑表名
     */
    private String logicTable;

    /**
     * 物理节点
     */
    private String actualDataNodes;

    /**
     * 分库策略
     */
    private ShardingStrategyProperties databaseShardingStrategy;

    /**
     * 分表策略
     */
    private ShardingStrategyProperties tableShardingStrategy;

    public String getLogicTable() {
        return logicTable;
    }

    public void setLogicTable(final String logicTable) {

        this.logicTable = logicTable;
    }

    public String getActualDataNodes() {
        return actualDataNodes;
    }

    public void setActualDataNodes(final String actualDataNodes) {

        this.actualDataNodes = actualDataNodes;
    }

    public ShardingStrategyProperties getDatabaseShardingStrategy() {
        return databaseShardingStrategy;
    }

    public void setDatabaseShardingStrategy(final ShardingStrategyProperties databaseShardingStrategy) {

        this.databaseShardingStrategy = databaseShardingStrategy;
    }

    public ShardingStrategyProperties getTableShardingStrategy() {
        return tableShardingStrategy;
    }

    public void setTableShardingStrategy(final ShardingStrategyProperties tableShardingStrategy) {

        this.tableShardingStrategy = tableShardingStrategy;
    }

}

