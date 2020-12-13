package org.ifinal.finalframework.annotation.sharding;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@InlineShardingStrategy(
    scope = ShardingStrategy.Scope.TABLE,
    columns = "age",
    expression = "${logicTable}_0${age % 2}")
@InlineShardingStrategy(
    scope = ShardingStrategy.Scope.DATABASE,
    columns = "name",
    expression = "${logicTable}_0${age % 2}")
public class ShardingEntity {

}
