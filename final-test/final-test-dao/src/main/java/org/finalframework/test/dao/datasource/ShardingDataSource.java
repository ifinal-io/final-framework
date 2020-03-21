package org.finalframework.test.dao.datasource;

import org.finalframework.coding.datasource.annotation.DataSource;
import org.finalframework.coding.datasource.annotation.ShardingRule;
import org.finalframework.test.entity.Person;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 19:57:24
 * @since 1.0
 */
@DataSource(prefix = "final.sharding", value = {"ds0", "ds1"})
@ShardingRule(tables = {Person.class})
public interface ShardingDataSource {
}
