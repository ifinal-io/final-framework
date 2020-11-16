package org.finalframework.boot.autoconfigure.sharding;


import java.io.Serializable;

/**
 * 行内分片策略
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-17 23:01:38
 * @see org.apache.shardingsphere.core.strategy.route.inline.InlineShardingStrategy
 * @since 1.0
 */
public class InlineShardingStrategyProperties implements Serializable {
    /**
     * 分片列名
     */
    private String shardingColumn;
    /**
     * 算法表达式
     */
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

