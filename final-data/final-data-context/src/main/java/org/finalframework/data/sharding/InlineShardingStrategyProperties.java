package org.finalframework.data.sharding;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 23:01:38
 * @since 1.0
 */
public class InlineShardingStrategyProperties implements Serializable {
    private String shardingColumn;
    private String algorithmExpression;

    public String getShardingColumn() {
        return shardingColumn;
    }

    public void setShardingColumn(String shardingColumn) {
        this.shardingColumn = shardingColumn;
    }

    public String getAlgorithmExpression() {
        return algorithmExpression;
    }

    public void setAlgorithmExpression(String algorithmExpression) {
        this.algorithmExpression = algorithmExpression;
    }
}

