/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.boot.autoconfigure.sharding;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import org.ifinalframework.data.jdbc.DataSourceFactory;
import org.ifinalframework.data.jdbc.DataSourceFactoryManager;
import org.ifinalframework.data.sharding.config.ShardingConfigurer;
import org.ifinalframework.data.sharding.config.ShardingDataSourceRegistry;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Map;

import lombok.Setter;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ShardingSphereDataSourceFactory.class)
@EnableConfigurationProperties({ShardingDataSourceProperties.class, DataSourceProperties.class})
public class ShardingDataSourceConfigurer implements ShardingConfigurer, EnvironmentAware {

    private static final String DEFAULT_DATASOURCE_NAME = "ds";

    private final ShardingDataSourceProperties properties;

    private final DataSourceProperties dataSourceProperties;

    private final DataSourceFactoryManager dataSourceFactoryManager = new DataSourceFactoryManager();

    @Setter
    private Environment environment;

    public ShardingDataSourceConfigurer(final ShardingDataSourceProperties properties,
                                        final DataSourceProperties dataSourceProperties) {
        this.properties = properties;
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public void addDataSource(final @NonNull ShardingDataSourceRegistry registry) throws SQLException {

        if (CollectionUtils.isEmpty(properties.getDatasource())) {
            registry.addDataSource(DEFAULT_DATASOURCE_NAME, create(dataSourceProperties, "spring.datasource"));
        } else {
            for (Map.Entry<String, DataSourceProperties> entry : properties.getDatasource().entrySet()) {
                final String prefix = ShardingDataSourceProperties.DEFAULT_DATASOURCE_PREFIX + ".datasource." + entry.getKey();
                final DataSource dataSource = create(entry.getValue(), prefix);
                registry.addDataSource(entry.getKey(), dataSource);
            }
        }

    }

    private DataSource create(DataSourceProperties properties, String prefix) throws SQLException {
        final DataSourceFactory<? extends DataSource> dataSourceFactory
                = dataSourceFactoryManager.getDataSourceFactory(properties.getType());
        return dataSourceFactory.create(properties, environment, prefix);

    }


}

