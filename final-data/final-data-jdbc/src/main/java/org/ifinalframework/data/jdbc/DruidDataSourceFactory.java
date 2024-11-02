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

package org.ifinalframework.data.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.sql.SQLException;
import java.util.Objects;

/**
 * DruidDataSourceFactory.
 *
 * @author iimik
 * @version 1.3.1
 * @see com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
 * @since 1.3.1
 */
public class DruidDataSourceFactory implements DataSourceFactory<DruidDataSource> {
    @NonNull
    @Override
    public DruidDataSource create(@NonNull DataSourceProperties properties, @NonNull Environment environment,
                                  @NonNull String prefix) throws SQLException {
        final Binder binder = Binder.get(environment);
        final BindResult<DruidDataSource> bindResult = binder.bind(prefix + ".druid", DruidDataSource.class);
        final DruidDataSource dataSource = bindResult.get();


        if (Objects.isNull(dataSource.getUrl())) {
            dataSource.setUrl(properties.determineUrl());
        }

        if (Objects.isNull(dataSource.getUsername())) {
            dataSource.setUsername(properties.determineUsername());
        }

        if (Objects.isNull(dataSource.getPassword())) {
            dataSource.setPassword(properties.determinePassword());
        }

        if (Objects.isNull(dataSource.getDriverClassName())) {
            dataSource.setDriverClassName(properties.getDriverClassName());
        }


        dataSource.init();
        return dataSource;
    }
}
