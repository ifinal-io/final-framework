package org.ifinal.finalframework.boot.autoconfigure.sharding;

import java.io.Serializable;

/**
 * 行内分片策略
 *
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration
 * @since 1.0.0
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

    public void setShardingColumn(final String shardingColumn) {

        this.shardingColumn = shardingColumn;
    }

    public String getAlgorithmExpression() {
        return algorithmExpression;
    }

    public void setAlgorithmExpression(final String algorithmExpression) {

        this.algorithmExpression = algorithmExpression;
    }

}

