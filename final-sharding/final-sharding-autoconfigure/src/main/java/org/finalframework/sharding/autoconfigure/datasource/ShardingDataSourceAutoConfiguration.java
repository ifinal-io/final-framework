/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.sharding.autoconfigure.datasource;


import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-11 11:10:40
 * @since 1.0
 */
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnMissingBean(DataSource.class)
@EnableConfigurationProperties(ShardingDataSourceProperties.class)
public class ShardingDataSourceAutoConfiguration {
    private final ShardingDataSourceProperties properties;

    public ShardingDataSourceAutoConfiguration(ShardingDataSourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DataSource shardingDataSource() throws SQLException {
        return properties.build();
    }
}

