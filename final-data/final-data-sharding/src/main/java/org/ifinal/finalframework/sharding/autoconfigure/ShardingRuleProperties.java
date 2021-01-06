package org.ifinal.finalframework.sharding.autoconfigure;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration
 * @since 1.0.0
 */
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

    public void setTables(final Map<String, TableRuleProperties> tables) {

        this.tables = tables;
    }

    public List<String> getBindingTables() {
        return bindingTables;
    }

    public void setBindingTables(final List<String> bindingTables) {

        this.bindingTables = bindingTables;
    }

    public List<String> getBroadcastTables() {
        return broadcastTables;
    }

    public void setBroadcastTables(final List<String> broadcastTables) {

        this.broadcastTables = broadcastTables;
    }

    public ShardingStrategyProperties getDefaultDatabaseShardingStrategy() {
        return defaultDatabaseShardingStrategy;
    }

    public void setDefaultDatabaseShardingStrategy(final ShardingStrategyProperties defaultDatabaseShardingStrategy) {

        this.defaultDatabaseShardingStrategy = defaultDatabaseShardingStrategy;
    }

    public ShardingStrategyProperties getDefaultTableShardingStrategy() {
        return defaultTableShardingStrategy;
    }

    public void setDefaultTableShardingStrategy(final ShardingStrategyProperties defaultTableShardingStrategy) {

        this.defaultTableShardingStrategy = defaultTableShardingStrategy;
    }

    public Map<String, MasterSlaveRuleProperties> getMasterSlaveRules() {
        return masterSlaveRules;
    }

    public void setMasterSlaveRules(final Map<String, MasterSlaveRuleProperties> masterSlaveRules) {

        this.masterSlaveRules = masterSlaveRules;
    }

}

