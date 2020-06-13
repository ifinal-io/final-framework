/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.sharding.autoconfigure.datasource;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 22:38:54
 * @since 1.0
 */
public class ShardingRuleProperties implements Serializable {
    private Map<String, TableRuleProperties> tables;
    private List<String> bindingTables;
    private List<String> broadcastTables;
    private ShardingStrategyProperties defaultDatabaseShardingStrategy;
    private ShardingStrategyProperties defaultTableShardingStrategy;
    private Map<String, MasterSlaveRuleProperties> masterSlaveRules;

    public Map<String, TableRuleProperties> getTables() {
        return tables;
    }

    public void setTables(Map<String, TableRuleProperties> tables) {
        this.tables = tables;
    }

    public List<String> getBindingTables() {
        return bindingTables;
    }

    public void setBindingTables(List<String> bindingTables) {
        this.bindingTables = bindingTables;
    }

    public List<String> getBroadcastTables() {
        return broadcastTables;
    }

    public void setBroadcastTables(List<String> broadcastTables) {
        this.broadcastTables = broadcastTables;
    }

    public ShardingStrategyProperties getDefaultDatabaseShardingStrategy() {
        return defaultDatabaseShardingStrategy;
    }

    public void setDefaultDatabaseShardingStrategy(ShardingStrategyProperties defaultDatabaseShardingStrategy) {
        this.defaultDatabaseShardingStrategy = defaultDatabaseShardingStrategy;
    }

    public ShardingStrategyProperties getDefaultTableShardingStrategy() {
        return defaultTableShardingStrategy;
    }

    public void setDefaultTableShardingStrategy(ShardingStrategyProperties defaultTableShardingStrategy) {
        this.defaultTableShardingStrategy = defaultTableShardingStrategy;
    }

    public Map<String, MasterSlaveRuleProperties> getMasterSlaveRules() {
        return masterSlaveRules;
    }

    public void setMasterSlaveRules(Map<String, MasterSlaveRuleProperties> masterSlaveRules) {
        this.masterSlaveRules = masterSlaveRules;
    }

}

