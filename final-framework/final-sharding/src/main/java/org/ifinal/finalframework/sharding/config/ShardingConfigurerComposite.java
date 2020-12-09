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


    public void addShardingConfigurers(Collection<ShardingConfigurer> shardingConfigurers){
        if (!CollectionUtils.isEmpty(shardingConfigurers)) {
            this.configurers.addAll(shardingConfigurers);
        }
    }

    @Override
    public void addDataSource(@NonNull ShardingDataSourceRegistry registry) {
        configurers.forEach(each -> each.addDataSource(registry));
    }

    @Override
    public void addShardingTable(@NonNull ShardingTableRegistry registry) {
        configurers.forEach(each -> each.addShardingTable(registry));
    }

    @Override
    public void addBindingTables(@NonNull BindingTableRegistry registry) {
        configurers.forEach(each -> each.addBindingTables(registry));

    }

    @Override
    public void addBroadcastTables(@NonNull BroadcastTableRegistry registry) {
        configurers.forEach(each -> each.addBroadcastTables(registry));

    }

    @Override
    public void addShardingAlgorithms(@NonNull ShardingAlgorithmRegistry registry) {
        configurers.forEach(each -> each.addShardingAlgorithms(registry));

    }
}
