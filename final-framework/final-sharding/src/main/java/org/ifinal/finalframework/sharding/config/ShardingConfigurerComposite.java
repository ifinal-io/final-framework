package org.ifinal.finalframework.sharding.config;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingConfigurerComposite implements ShardingConfigurer {
    private final List<ShardingConfigurer> delegates = new ArrayList<>();


    @Override
    public void addShardingTableRule(@NonNull ShardingTableRuleRegistry registry) {
        delegates.forEach(each -> each.addShardingTableRule(registry));
    }

    @Override
    public void addBindingTables(@NonNull BindingTableRegistry registry) {
        delegates.forEach(each -> each.addBindingTables(registry));

    }

    @Override
    public void addBroadcastTables(@NonNull BroadcastTableRegistry registry) {
        delegates.forEach(each -> each.addBroadcastTables(registry));

    }

    @Override
    public void addShardingAlgorithms(@NonNull ShardingAlgorithmRegistry registry) {
        delegates.forEach(each -> each.addShardingAlgorithms(registry));

    }
}
