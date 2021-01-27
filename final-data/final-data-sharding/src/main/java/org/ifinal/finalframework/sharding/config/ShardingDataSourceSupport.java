package org.ifinal.finalframework.sharding.config;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class ShardingDataSourceSupport {

    private final ShardingConfigurerComposite composite = new ShardingConfigurerComposite();

    @Autowired(required = false)
    public void setConfigurers(final List<ShardingConfigurer> configurers) {

        if (logger.isInfoEnabled()) {
            logger.info("found ShardingConfigurer size={}", configurers.size());
        }

        if (!CollectionUtils.isEmpty(configurers)) {

            if (logger.isInfoEnabled()) {
                for (final ShardingConfigurer configurer : configurers) {
                    logger.info("found ShardingConfigurer: {}", configurer.getClass().getName());
                }
            }

            this.composite.addShardingConfigurers(configurers);
        }
    }

    @Bean
    protected DataSource dataSource() throws SQLException {

        ShardingConfiguration shardingConfiguration = getShardingDataSourceConfiguration();

        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();

        for (ShardingTableRegistration registration : shardingConfiguration.getTables()) {
            ShardingTableRuleConfiguration shardingTableRuleConfiguration = new ShardingTableRuleConfiguration(
                    registration.getLogicTable(),
                    registration.getActualDataNodes());

            ShardingStrategyRegistration databaseShardingStrategy = registration.getDatabaseShardingStrategy();
            if (Objects.nonNull(databaseShardingStrategy)) {
                shardingTableRuleConfiguration
                        .setDatabaseShardingStrategy(buildShardingStrategy(databaseShardingStrategy));
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
        props.put("sql-show", true);

        return ShardingSphereDataSourceFactory
                .createDataSource(shardingConfiguration.getDatasource(), Collections.singleton(configuration), props);

    }

    protected ShardingConfiguration getShardingDataSourceConfiguration() {

        ShardingConfiguration configuration = ShardingConfiguration.builder()
                .datasource(Collections.unmodifiableMap(getDataSourceRegistry().getDataSources()))
                .tables(Collections.unmodifiableCollection(getShardingTableRegistry().getTables()))
                .shardingAlgorithms(
                        Collections.unmodifiableCollection(getShardingAlgorithmRegistry().getShardingAlgorithms()))
                .build();

        log(configuration);

        return configuration;

    }

    private void log(final ShardingConfiguration configuration) {

        if (!logger.isInfoEnabled()) {
            return;
        }

        logger.info("------------------- final sharding configuration -------------------");
        logger.info("├─final:");
        logger.info("  ├─sharing:");
        logger.info("  │ ├─datasource:");
        logger.info("  │ │ └─names: {}", String.join(",", configuration.getDatasource().keySet()));
        logger.info("  │ ├─rules:");
        logger.info("  │ │ ├─tables:");

        if (!configuration.getTables().isEmpty()) {
            for (ShardingTableRegistration table : configuration.getTables()) {
                logger.info("  │ │ │ ├─{}:", table.getLogicTable());
                logger.info("  │ │ │ │ ├─actual-data-nodes: {}", table.getActualDataNodes());
            }
        }

        if (!configuration.getShardingAlgorithms().isEmpty()) {
            logger.info("  │ │ ├─sharding-algorithms:");
            for (ShardingAlgorithmRegistration shardingAlgorithm : configuration.getShardingAlgorithms()) {
                logger.info("  │ │ │ ├─{}:", shardingAlgorithm.getName());
                logger.info("  │ │ │ │ ├─type: {}", shardingAlgorithm.getType());
                logger.info("  │ │ │ │ └─props:");
                for (Map.Entry<Object, Object> entry : shardingAlgorithm.getProperties().entrySet()) {
                    logger.info("  │ │ │ │ │ ├─{}: {}", entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }

    }

    private ShardingAlgorithmRegistry getShardingAlgorithmRegistry() {
        ShardingAlgorithmRegistry registry = new ShardingAlgorithmRegistry();
        composite.addShardingAlgorithms(registry);
        return registry;
    }

    private ShardingSphereAlgorithmConfiguration buildShardingAlgorithm(
            final ShardingAlgorithmRegistration databaseShardingStrategy) {

        return new ShardingSphereAlgorithmConfiguration(databaseShardingStrategy.getType(),
                databaseShardingStrategy.getProperties());
    }

    private ShardingStrategyConfiguration buildShardingStrategy(
            final ShardingStrategyRegistration shardingStrategyRegistration) {

        String columns = String.join(",", shardingStrategyRegistration.getColumns());
        switch (shardingStrategyRegistration.getStrategy()) {
            case STANDARD:
                return new StandardShardingStrategyConfiguration(columns, shardingStrategyRegistration.getName());
            case HINT:
                return new HintShardingStrategyConfiguration(shardingStrategyRegistration.getName());
            case COMPLEX:
                return new ComplexShardingStrategyConfiguration(columns, shardingStrategyRegistration.getName());
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
