package org.finalframework.coding.datasource.annotation;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分片规则
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-14 10:10:20
 * @see ShardingRuleConfiguration
 * @see ShardingDataSourceFactory
 * @see MasterSlaveRule
 * @since 1.0
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ShardingRule {

    Class<?>[] tables();

    /**
     * @return
     * @see ShardingRuleConfiguration#masterSlaveRuleConfigs
     */
    MasterSlaveRule[] masterSlaveRule() default {};

}
