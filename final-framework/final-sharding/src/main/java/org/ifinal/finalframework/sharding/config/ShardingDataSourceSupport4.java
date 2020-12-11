package org.ifinal.finalframework.sharding.config;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.ifinal.finalframework.annotation.sharding.Property;
import org.ifinal.finalframework.annotation.sharding.ShardingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingDataSourceSupport4 {
    private final ShardingConfigurerComposite composite = new ShardingConfigurerComposite();


    @Autowired(required = false)
    public void setConfigurers(List<ShardingConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            this.composite.addShardingConfigurers(configurers);
        }
    }


    @Bean
    protected DataSource dataSource() throws SQLException {

        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();

        final ShardingDataSourceRegistry dataSourceRegistry = getDataSourceRegistry();

        final ShardingTableRegistry tableRegistry = getShardingTableRegistry();


        for (ShardingTableRegistration registration : tableRegistry.getTables()) {


            TableRuleConfiguration shardingTableRuleConfiguration = new TableRuleConfiguration(registration.getLogicTable(), registration.getActualDataNodes());

            ShardingStrategyRegistration databaseShardingStrategy = registration.getDatabaseShardingStrategy();
            if (Objects.nonNull(databaseShardingStrategy)) {
                shardingTableRuleConfiguration.setDatabaseShardingStrategyConfig(buildShardingStrategy(databaseShardingStrategy));
            }

            ShardingStrategyRegistration tableShardingStrategy = registration.getTableShardingStrategy();
            if (Objects.nonNull(tableShardingStrategy)) {
                shardingTableRuleConfiguration.setTableShardingStrategyConfig(buildShardingStrategy(tableShardingStrategy));
            }

            configuration.getTableRuleConfigs().add(shardingTableRuleConfiguration);
        }


        Properties props = new Properties();
        props.put("sql.show", true);
        return ShardingDataSourceFactory.createDataSource(dataSourceRegistry.getDataSources(), configuration, props);


    }


    private ShardingStrategyConfiguration buildShardingStrategy(ShardingStrategyRegistration shardingStrategyRegistration) {
        switch (shardingStrategyRegistration.getType()) {
            case ShardingStrategy.Algorithm.INLINE:
                return new InlineShardingStrategyConfiguration(shardingStrategyRegistration.getColumns()[0], shardingStrategyRegistration.getProperties().getProperty(Property.INLINE_ALGORITHM_EXPRESSION));
            default:
                throw new IllegalArgumentException(shardingStrategyRegistration.getType());
        }
    }


    private ShardingDataSourceRegistry getDataSourceRegistry() {
        ShardingDataSourceRegistry registry = new ShardingDataSourceRegistry();
        composite.addDataSource(registry);
        return registry;
    }

    private ShardingTableRegistry getShardingTableRegistry() {
        ShardingTableRegistry registry = new ShardingTableRegistry();
        composite.addShardingTable(registry);
        return registry;
    }


}
