package org.finalframework.coding.datasource.spring.metadata;


import org.finalframework.coding.datasource.processer.DataSourceAutoConfiguration;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-12 13:58:47
 * @since 1.0
 */
public class DataSourceAutoConfigurationMetaData extends ConfigurationMetadata {
    private final DataSourceAutoConfiguration configuration;

    public DataSourceAutoConfigurationMetaData(DataSourceAutoConfiguration configuration) {
        this.configuration = configuration;
        init();
    }

    private void init() {
        configuration.getDataSources()
                .stream()
                .map(it -> new DataSourceMetaData(configuration.getPrefix() + ".datasource", it))
                .forEach(this::merge);

        if (configuration.getShardingRule() != null) {
            this.merge(new ShardingRuleMetaData(configuration.getPrefix() + ".sharding-rule", configuration.getShardingRule().getTables()));
        }
    }
}

