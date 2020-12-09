package org.ifinal.finalframework.boot.autoconfigure.sharding;


import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
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
                List<ShardingTableRuleConfiguration> tableRuleConfigurations = shardingRule.getTables()
                        .values()
                        .stream()
                        .map(tableRule -> {
                            final ShardingTableRuleConfiguration tableRuleConfiguration = new ShardingTableRuleConfiguration(tableRule.getLogicTable(), tableRule.getActualDataNodes());

                            tableRuleConfiguration.setDatabaseShardingStrategy(buildShardingStrategyConfiguration(tableRule.getDatabaseShardingStrategy()));
                            tableRuleConfiguration.setTableShardingStrategy(buildShardingStrategyConfiguration(tableRule.getTableShardingStrategy()));
                            return tableRuleConfiguration;
                        })
                        .collect(Collectors.toList());


                configuration.getTables().addAll(tableRuleConfigurations);





            }

            if (Asserts.nonEmpty(shardingRule.getBindingTables())) {
                configuration.getBindingTableGroups().addAll(shardingRule.getBindingTables());
            }

            if (Asserts.nonEmpty(shardingRule.getBroadcastTables())) {
                configuration.getBroadcastTables().addAll(shardingRule.getBroadcastTables());
            }

//            if (Asserts.nonEmpty(shardingRule.getMasterSlaveRules())) {
//                List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = shardingRule.getMasterSlaveRules().values()
//                        .stream()
//                        .map(it -> new MasterSlaveRuleConfiguration(it.getName(), it.getMaster(), it.getSlaves()))
//                        .collect(Collectors.toList());
//
//                configuration.getMasterSlaveRuleConfigs().addAll(masterSlaveRuleConfigurations);
//            }



            return ShardingSphereDataSourceFactory.createDataSource(buildDataSourceMap(), Collections.singleton(configuration), properties);
        } else {
//            if (masterSlaveRule != null) {
//                MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(masterSlaveRule.getName(), masterSlaveRule.getMaster(), masterSlaveRule.getSlaves());
//                return MasterSlaveDataSourceFactory.createDataSource(buildDataSourceMap(), masterSlaveRuleConfig, properties);
//            }
        }

        throw new IllegalArgumentException("");
    }


    private ShardingStrategyConfiguration buildShardingStrategyConfiguration(ShardingStrategyProperties shardingStrategyProperties) {
        if (shardingStrategyProperties == null) return null;

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

