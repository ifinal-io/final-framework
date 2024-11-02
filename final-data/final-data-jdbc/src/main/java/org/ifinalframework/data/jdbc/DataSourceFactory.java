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

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

import java.sql.SQLException;

/**
 * 数据源工厂.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@FunctionalInterface
public interface DataSourceFactory<T extends DataSource> {
    /**
     * return a {@link DataSource} instance.
     *
     * @param properties  data source properties.
     * @param environment application environment.
     * @param prefix      data source configuration prefix.
     * @return data source instance.
     * @throws SQLException when create data source exception.
     */
    @NonNull
    T create(@NonNull DataSourceProperties properties, @NonNull Environment environment, @NonNull String prefix) throws SQLException;

}
