package org.finalframework.data.sharding;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 22:40:33
 * @since 1.0
 */
public class TableRuleProperties implements Serializable {
    private String logicTable;
    private String actualDataNodes;
    private ShardingStrategyProperties databaseShardingStrategy;
    private ShardingStrategyProperties tableShardingStrategy;


    public String getLogicTable() {
        return logicTable;
    }

    public void setLogicTable(String logicTable) {
        this.logicTable = logicTable;
    }

    public String getActualDataNodes() {
        return actualDataNodes;
    }

    public void setActualDataNodes(String actualDataNodes) {
        this.actualDataNodes = actualDataNodes;
    }

    public ShardingStrategyProperties getDatabaseShardingStrategy() {
        return databaseShardingStrategy;
    }

    public void setDatabaseShardingStrategy(ShardingStrategyProperties databaseShardingStrategy) {
        this.databaseShardingStrategy = databaseShardingStrategy;
    }

    public ShardingStrategyProperties getTableShardingStrategy() {
        return tableShardingStrategy;
    }

    public void setTableShardingStrategy(ShardingStrategyProperties tableShardingStrategy) {
        this.tableShardingStrategy = tableShardingStrategy;
    }
}

