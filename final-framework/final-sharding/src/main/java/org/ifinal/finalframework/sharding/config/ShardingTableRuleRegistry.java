package org.ifinal.finalframework.sharding.config;

import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.ifinal.finalframework.sharding.annotation.ShardingTable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @see ShardingTable
 * @since 1.0.0
 */
public class ShardingTableRuleRegistry {
    private Collection<ShardingTableRuleConfiguration> tables = new ArrayList<>();


    public ShardingTableRuleRegistration addShardingTableRule(String logicTable, String[] actualDataNodes) {
        ShardingTableRuleRegistration registration = new ShardingTableRuleRegistration(logicTable, actualDataNodes);
        tables.add(registration.getConfiguration());
        return registration;
    }


}
