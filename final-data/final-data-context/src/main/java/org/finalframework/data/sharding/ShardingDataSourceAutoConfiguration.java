package org.finalframework.data.sharding;


import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.finalframework.core.Assert;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 23:54:23
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@ConditionalOnProperty(prefix = "final.sharding", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ShardingDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class ShardingDataSourceAutoConfiguration {
    private final ShardingDataSourceProperties properties;

    public ShardingDataSourceAutoConfiguration(ShardingDataSourceProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (Assert.nonEmpty(properties.getShardingRule()) && Assert.nonEmpty(properties.getShardingRule().getTableRules())) {
            for (Map.Entry<String, TableRuleProperties> entry : properties.getShardingRule().getTableRules().entrySet()) {
                if (Assert.isEmpty(entry.getValue().getLogicTable())) {
                    entry.getValue().setLogicTable(entry.getKey());
                }
            }
        }
    }

    @Bean
    public DataSource dataSource() throws SQLException {

        final ShardingRuleProperties shardingRule = properties.getShardingRule();
        final MasterSlaveRuleProperties masterSlaveRule = properties.getMasterSlaveRule();

        if (shardingRule != null) {
            ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();
            if (Assert.nonEmpty(shardingRule.getTableRules())) {
                List<TableRuleConfiguration> tableRuleConfigurations = shardingRule.getTableRules()
                        .values()
                        .stream()
                        .map(tableRule -> {
                            final TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(tableRule.getLogicTable(), tableRule.getActualDataNodes());

                            tableRuleConfiguration.setDatabaseShardingStrategyConfig(buildShardingStrategyConfiguration(tableRule.getTableShardingStrategy()));
                            tableRuleConfiguration.setTableShardingStrategyConfig(buildShardingStrategyConfiguration(tableRule.getTableShardingStrategy()));
                            return tableRuleConfiguration;
                        })
                        .collect(Collectors.toList());


                configuration.getTableRuleConfigs().addAll(tableRuleConfigurations);
            }

            if (Assert.nonEmpty(shardingRule.getBindingTables())) {
                configuration.getBindingTableGroups().addAll(shardingRule.getBindingTables());
            }

            if (Assert.nonEmpty(shardingRule.getBroadcastTables())) {
                configuration.getBroadcastTables().addAll(shardingRule.getBroadcastTables());
            }

            if (Assert.nonEmpty(shardingRule.getMasterSlaveRules())) {
                List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = shardingRule.getMasterSlaveRules().values()
                        .stream()
                        .map(it -> new MasterSlaveRuleConfiguration(it.getName(), it.getMaster(), it.getSlaves()))
                        .collect(Collectors.toList());

                configuration.getMasterSlaveRuleConfigs().addAll(masterSlaveRuleConfigurations);
            }


            return ShardingDataSourceFactory.createDataSource(buildDataSourceMap(), configuration, properties.getProperties());
        } else {
            if (masterSlaveRule != null) {
                MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(masterSlaveRule.getName(), masterSlaveRule.getMaster(), masterSlaveRule.getSlaves());
                return MasterSlaveDataSourceFactory.createDataSource(buildDataSourceMap(), masterSlaveRuleConfig, properties.getProperties());
            }
        }

        throw new IllegalArgumentException("");


    }

    private ShardingStrategyConfiguration buildShardingStrategyConfiguration(ShardingStrategyProperties shardingStrategyProperties) {
        if (shardingStrategyProperties == null) return null;

        InlineShardingStrategyProperties inline = shardingStrategyProperties.getInline();
        if (inline != null) {
            return new InlineShardingStrategyConfiguration(inline.getShardingColumn(), inline.getAlgorithmExpression());
        }

        return null;
    }

    private Map<String, DataSource> buildDataSourceMap() {
        final Map<String, DataSource> dataSourceMap = new HashMap<>();

        for (Map.Entry<String, DataSourceProperties> entry : properties.getDatasource().entrySet()) {
            dataSourceMap.put(entry.getKey(), entry.getValue().initializeDataSourceBuilder().build());
        }
        return dataSourceMap;
    }


}

