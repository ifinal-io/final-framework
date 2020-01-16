package org.finalframework.coding.datasource.annotation;

import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;

/**
 * 行内分片策略
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-15 16:16:59
 * @see InlineShardingStrategyConfiguration
 * @since 1.0
 */
public @interface InlineShardingStrategy {
    /**
     * @return 分片列
     * @see InlineShardingStrategyConfiguration#shardingColumn
     */
    String shardingColumn();

    /**
     * @return 分片表达式
     * @see InlineShardingStrategyConfiguration#algorithmExpression
     */
    String algorithmExpression();
}
