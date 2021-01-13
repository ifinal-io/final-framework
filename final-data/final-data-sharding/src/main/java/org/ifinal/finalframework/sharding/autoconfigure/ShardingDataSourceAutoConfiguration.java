package org.ifinal.finalframework.sharding.autoconfigure;

import org.ifinal.finalframework.sharding.config.ShardingDataSourceSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = ShardingDataSourceProperties.DEFAULT_DATASOURCE_PREFIX, name = "enable", havingValue = "true")
public class ShardingDataSourceAutoConfiguration extends ShardingDataSourceSupport {

}
