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

import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DataSourceFactoryManager.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
public final class DataSourceFactoryManager {

    private final DataSourceFactory<DataSource> defaultDataSourceFactory = (properties, environment, prefix)
            -> properties.initializeDataSourceBuilder().build();

    private final Map<String, DataSourceFactory<? extends DataSource>> cache = new LinkedHashMap<>();

    public DataSourceFactoryManager() {
        final ClassLoader classLoader = DataSourceFactoryManager.class.getClassLoader();

        if (ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", classLoader)) {
            cache.put("com.alibaba.druid.pool.DruidDataSource", new DruidDataSourceFactory());
        }

    }

    @NonNull
    public <T extends DataSource> DataSourceFactory<? extends DataSource> getDataSourceFactory(Class<T> clazz) {

        if (Objects.isNull(clazz)) {
            return defaultDataSourceFactory;
        }

        return cache.getOrDefault(clazz.getName(), defaultDataSourceFactory);
    }


}
