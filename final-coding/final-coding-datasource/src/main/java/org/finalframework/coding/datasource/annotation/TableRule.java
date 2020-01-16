package org.finalframework.coding.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-14 10:12:04
 * @since 1.0
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface TableRule {
    /**
     * 逻辑表名
     */
    String logicTable();

    /**
     * 物理节点表达式
     */
    String[] actualDataNodes();

    /**
     * 分库策略
     */
    ShardingStrategy databaseShardingStrategy();

    /**
     * 分表策略
     */
    ShardingStrategy tableShardingStrategy();

}
