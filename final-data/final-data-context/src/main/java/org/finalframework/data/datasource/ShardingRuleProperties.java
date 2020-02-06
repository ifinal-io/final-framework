package org.finalframework.data.datasource;


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

