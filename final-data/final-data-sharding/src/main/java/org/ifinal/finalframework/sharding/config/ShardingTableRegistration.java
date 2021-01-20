package org.ifinal.finalframework.sharding.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * @author likly
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
