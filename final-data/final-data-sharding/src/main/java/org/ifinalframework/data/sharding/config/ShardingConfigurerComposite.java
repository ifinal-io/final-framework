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

package org.ifinalframework.data.sharding.config;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ShardingConfigurerComposite implements ShardingConfigurer {

    private final Collection<ShardingConfigurer> configurers = new ArrayList<>();

    public void addShardingConfigurers(final Collection<ShardingConfigurer> shardingConfigurers) {

        if (!CollectionUtils.isEmpty(shardingConfigurers)) {
            this.configurers.addAll(shardingConfigurers);
        }
    }

    @Override
    public void addDataSource(final @NonNull ShardingDataSourceRegistry registry) throws SQLException {

        for (ShardingConfigurer each : configurers) {
            each.addDataSource(registry);
        }
    }

    @Override
    public void addShardingTable(final @NonNull ShardingTableRegistry registry) {

        configurers.forEach(each -> each.addShardingTable(registry));
    }

    @Override
    public void addBindingTables(final @NonNull BindingTableRegistry registry) {

        configurers.forEach(each -> each.addBindingTables(registry));

    }

    @Override
    public void addBroadcastTables(final @NonNull BroadcastTableRegistry registry) {

        configurers.forEach(each -> each.addBroadcastTables(registry));

    }

    @Override
    public void addShardingAlgorithms(final @NonNull ShardingAlgorithmRegistry registry) {

        configurers.forEach(each -> each.addShardingAlgorithms(registry));

    }

}
