package org.ifinal.finalframework.sharding.config;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
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
    public void addDataSource(final @NonNull ShardingDataSourceRegistry registry) {

        configurers.forEach(each -> each.addDataSource(registry));
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
