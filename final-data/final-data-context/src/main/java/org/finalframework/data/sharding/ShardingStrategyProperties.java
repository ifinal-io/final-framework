package org.finalframework.data.sharding;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 23:02:39
 * @since 1.0
 */
public class ShardingStrategyProperties implements Serializable {
    private static final long serialVersionUID = 6419098165263663657L;
    private InlineShardingStrategyProperties inline;

    public InlineShardingStrategyProperties getInline() {
        return inline;
    }

    public void setInline(InlineShardingStrategyProperties inline) {
        this.inline = inline;
    }
}

