package org.ifinal.finalframework.sharding.config;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShardingDataSourceSupport {
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
            ShardingTableRuleConfiguration shardingTableRuleConfiguration = new ShardingTableRuleConfiguration(registration.getLogicTable(), registration.getActualDataNodes());

            ShardingStrategyRegistration databaseShardingStrategy = registration.getDatabaseShardingStrategy();
            if (Objects.nonNull(databaseShardingStrategy)) {
                shardingTableRuleConfiguration.setDatabaseShardingStrategy(buildShardingStrategy(databaseShardingStrategy));
            }

            ShardingStrategyRegistration tableShardingStrategy = registration.getTableShardingStrategy();
            if (Objects.nonNull(tableShardingStrategy)) {
                shardingTableRuleConfiguration.setTableShardingStrategy(buildShardingStrategy(tableShardingStrategy));
            }


            configuration.getTables().add(shardingTableRuleConfiguration);
        }

        final ShardingAlgorithmRegistry shardingAlgorithmRegistry = getShardingAlgorithmRegistry();

        for (ShardingAlgorithmRegistration shardingAlgorithm : shardingAlgorithmRegistry.getShardingAlgorithms()) {
            configuration.getShardingAlgorithms().put(shardingAlgorithm.getName(),
                    buildShardingAlgorithm(shardingAlgorithm));
        }

        Properties props = new Properties();
        props.put("sql-show",true);
        return ShardingSphereDataSourceFactory.createDataSource(dataSourceRegistry.getDataSources(), Collections.singleton(configuration), props);


    }

    private ShardingAlgorithmRegistry getShardingAlgorithmRegistry() {
        ShardingAlgorithmRegistry registry = new ShardingAlgorithmRegistry();
        composite.addShardingAlgorithms(registry);
        return registry;
    }

    private ShardingSphereAlgorithmConfiguration buildShardingAlgorithm(ShardingAlgorithmRegistration databaseShardingStrategy) {
        return new ShardingSphereAlgorithmConfiguration(databaseShardingStrategy.getType().name(), databaseShardingStrategy.getProperties());
    }

    private ShardingStrategyConfiguration buildShardingStrategy(ShardingStrategyRegistration shardingStrategyRegistration) {
        switch (shardingStrategyRegistration.getType()) {
            case INLINE:
                return new StandardShardingStrategyConfiguration(shardingStrategyRegistration.getColumn(), shardingStrategyRegistration.getName());
            default:
                throw new IllegalArgumentException(shardingStrategyRegistration.getType().name());
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
