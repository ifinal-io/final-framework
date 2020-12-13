package org.ifinal.finalframework.boot.autoconfigure.sharding;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 分片策略
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class ShardingStrategyProperties implements Serializable {

    private static final long serialVersionUID = 6419098165263663657L;

    /**
     * 行内分片策略
     */
    private InlineShardingStrategyProperties inline;

}

