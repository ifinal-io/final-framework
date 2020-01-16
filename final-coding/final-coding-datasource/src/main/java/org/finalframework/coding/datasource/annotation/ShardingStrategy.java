package org.finalframework.coding.datasource.annotation;

/**
 * 分片策略
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-15 15:50:07
 * @since 1.0
 */
public @interface ShardingStrategy {

    /**
     * 行内分片策略
     */
    InlineShardingStrategy inline();

}
