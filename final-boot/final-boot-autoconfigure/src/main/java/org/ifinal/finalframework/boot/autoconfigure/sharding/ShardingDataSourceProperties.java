package org.ifinal.finalframework.boot.autoconfigure.sharding;


import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "final.sharding")
public class ShardingDataSourceProperties {

    /**
     * 数据源
     */
    private Map<String, DataSourceProperties> datasource;
    /**
     * 分片规则
     */
    private ShardingRuleProperties shardingRule;
    /**
     * 读写分离
     */
    private MasterSlaveRuleProperties masterSlaveRule;
    private Properties properties;


    public Map<String, DataSourceProperties> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSourceProperties> datasource) {
        this.datasource = datasource;
    }

    public ShardingRuleProperties getShardingRule() {
        return shardingRule;
    }


    public MasterSlaveRuleProperties getMasterSlaveRule() {
        return masterSlaveRule;
    }


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public DataSource build() throws SQLException {
        if (shardingRule != null) {
            ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();
            if (Asserts.nonEmpty(shardingRule.getTables())) {
                List<TableRuleConfiguration> tableRuleConfigurations = shardingRule.getTables()
                        .values()
                        .stream()
                        .map(tableRule -> {
                            final TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(tableRule.getLogicTable(), tableRule.getActualDataNodes());

                            tableRuleConfiguration.setDatabaseShardingStrategyConfig(buildShardingStrategyConfiguration(tableRule.getDatabaseShardingStrategy()));
                            tableRuleConfiguration.setTableShardingStrategyConfig(buildShardingStrategyConfiguration(tableRule.getTableShardingStrategy()));
                            return tableRuleConfiguration;
                        })
                        .collect(Collectors.toList());


                configuration.getTableRuleConfigs().addAll(tableRuleConfigurations);
            }

            if (Asserts.nonEmpty(shardingRule.getBindingTables())) {
                configuration.getBindingTableGroups().addAll(shardingRule.getBindingTables());
            }

            if (Asserts.nonEmpty(shardingRule.getBroadcastTables())) {
                configuration.getBroadcastTables().addAll(shardingRule.getBroadcastTables());
            }

            if (Asserts.nonEmpty(shardingRule.getMasterSlaveRules())) {
                List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = shardingRule.getMasterSlaveRules().values()
                        .stream()
                        .map(it -> new MasterSlaveRuleConfiguration(it.getName(), it.getMaster(), it.getSlaves()))
                        .collect(Collectors.toList());

                configuration.getMasterSlaveRuleConfigs().addAll(masterSlaveRuleConfigurations);
            }


            return ShardingDataSourceFactory.createDataSource(buildDataSourceMap(), configuration, properties);
        } else {
            if (masterSlaveRule != null) {
                MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(masterSlaveRule.getName(), masterSlaveRule.getMaster(), masterSlaveRule.getSlaves());
                return MasterSlaveDataSourceFactory.createDataSource(buildDataSourceMap(), masterSlaveRuleConfig, properties);
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

        for (Map.Entry<String, DataSourceProperties> entry : datasource.entrySet()) {
            dataSourceMap.put(entry.getKey(), entry.getValue().initializeDataSourceBuilder().build());
        }
        return dataSourceMap;
    }
}

