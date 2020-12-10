package org.ifinal.finalframework.annotation.sharding;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ShardingStrategyTest {

    @Test
    void shardingStrategy(){
        Set<ShardingStrategy> allMergedAnnotations = AnnotatedElementUtils.findMergedRepeatableAnnotations(ShardingEntity.class, ShardingStrategy.class);
        Set<InlineShardingStrategy> allMergedAnnotations2 = AnnotatedElementUtils.findMergedRepeatableAnnotations(ShardingEntity.class, InlineShardingStrategy.class);
        System.out.println();
    }

}
