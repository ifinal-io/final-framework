package org.ifinal.finalframework.boot.autoconfigure.sharding;

import org.ifinal.finalframework.sharding.config.ShardingConfigurer;
import org.ifinal.finalframework.sharding.config.ShardingDataSourceSupport;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnClass(ShardingConfigurer.class)
public class ShardingDataSourceAutoConfiguration extends ShardingDataSourceSupport {

}
