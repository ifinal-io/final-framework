package org.ifinal.finalframework.boot.autoconfigure.sharding;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
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

