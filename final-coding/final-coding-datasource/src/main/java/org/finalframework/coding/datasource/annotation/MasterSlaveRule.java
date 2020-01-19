package org.finalframework.coding.datasource.annotation;

import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读写分离
 *
 * @author likly
 * @version 1.0
 * @date 2020-01-16 10:29:23
 * @see MasterSlaveRuleConfiguration
 * @see ShardingRule
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MasterSlaveRule {
    /**
     * @see MasterSlaveRuleConfiguration#name
     */
    String name();

    /**
     * @see MasterSlaveRuleConfiguration#masterDataSourceName
     */
    String master();

    /**
     * @see MasterSlaveRuleConfiguration#slaveDataSourceNames
     */
    String[] slaves();
}
