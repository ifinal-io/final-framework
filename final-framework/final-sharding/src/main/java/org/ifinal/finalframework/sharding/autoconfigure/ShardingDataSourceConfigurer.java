package org.ifinal.finalframework.sharding.autoconfigure;

import java.util.Map;
import org.ifinal.finalframework.sharding.config.ShardingConfigurer;
import org.ifinal.finalframework.sharding.config.ShardingDataSourceRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ShardingDataSourceProperties.class, DataSourceProperties.class})
public class ShardingDataSourceConfigurer implements ShardingConfigurer {

    private static final String DEFAULT_DATASOURCE_NAME = "ds";

    private final ShardingDataSourceProperties properties;

    private final DataSourceProperties dataSourceProperties;

    public ShardingDataSourceConfigurer(final ShardingDataSourceProperties properties, final DataSourceProperties dataSourceProperties) {
        this.properties = properties;
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public void addDataSource(final @NonNull ShardingDataSourceRegistry registry) {

        if (CollectionUtils.isEmpty(properties.getDatasource())) {
            registry.addDataSource(DEFAULT_DATASOURCE_NAME, dataSourceProperties.initializeDataSourceBuilder().build());
        } else {
            for (Map.Entry<String, DataSourceProperties> entry : properties.getDatasource().entrySet()) {
                registry.addDataSource(entry.getKey(), entry.getValue().initializeDataSourceBuilder().build());
            }
        }

    }

}

