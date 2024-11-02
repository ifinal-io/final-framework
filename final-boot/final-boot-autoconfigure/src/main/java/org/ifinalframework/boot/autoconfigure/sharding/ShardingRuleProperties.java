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

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author iimik
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "final.sharding.rule")
public class ShardingRuleProperties implements Serializable {

    /**
     * @see ShardingRuleConfiguration#setTables(Collection)
     */
    private Map<String, TableRuleProperties> tables;

    private List<String> bindingTables;

    private List<String> broadcastTables;

    private ShardingStrategyProperties defaultDatabaseShardingStrategy;

    private ShardingStrategyProperties defaultTableShardingStrategy;

    private Map<String, MasterSlaveRuleProperties> masterSlaveRules;

    public Map<String, TableRuleProperties> getTables() {
        return tables;
    }

}

