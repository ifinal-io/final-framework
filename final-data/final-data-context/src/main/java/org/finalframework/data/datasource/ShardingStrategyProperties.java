package org.finalframework.data.datasource;


import java.io.Serializable;

/**
 * 分片策略
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-17 23:02:39
 * @since 1.0
 */
public class ShardingStrategyProperties implements Serializable {
    private static final long serialVersionUID = 6419098165263663657L;
    /**
     * 行内分片策略
     */
    private InlineShardingStrategyProperties inline;

    public InlineShardingStrategyProperties getInline() {
        return inline;
    }

    public void setInline(InlineShardingStrategyProperties inline) {
        this.inline = inline;
    }
}

