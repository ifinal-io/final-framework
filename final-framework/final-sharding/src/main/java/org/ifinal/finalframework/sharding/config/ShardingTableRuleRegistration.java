package org.ifinal.finalframework.sharding.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingTableRuleRegistration {
    @Getter(AccessLevel.PACKAGE)
    private final ShardingTableRuleConfiguration configuration;

    public ShardingTableRuleRegistration(final String logicTable) {
        this.configuration = new ShardingTableRuleConfiguration(logicTable);
    }

    public ShardingTableRuleRegistration(final String logicTable, final String... actualDataNodes) {
        this.configuration = new ShardingTableRuleConfiguration(logicTable, String.join(",", actualDataNodes));
    }

    public ShardingTableRuleRegistration setStandardDatabaseShardingStrategy(final String shardingColumn, final String shardingAlgorithmName) {
        return setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration(shardingColumn, shardingAlgorithmName));
    }

    public ShardingTableRuleRegistration setDatabaseShardingStrategy(final ShardingStrategyConfiguration shardingStrategy) {
        this.configuration.setDatabaseShardingStrategy(shardingStrategy);
        return this;
    }

    public ShardingTableRuleRegistration setTableStandardShardingStrategy(final String shardingColumn, final String shardingAlgorithmName) {
        return setTableShardingStrategy(new StandardShardingStrategyConfiguration(shardingColumn, shardingAlgorithmName));
    }

    public ShardingTableRuleRegistration setTableShardingStrategy(final ShardingStrategyConfiguration shardingStrategy) {
        this.configuration.setTableShardingStrategy(shardingStrategy);
        return this;
    }


}
