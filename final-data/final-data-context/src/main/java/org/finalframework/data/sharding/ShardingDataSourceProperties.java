package org.finalframework.data.sharding;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-18 12:33:26
 * @since 1.0
 */
@ConfigurationProperties("final.sharding")
public class ShardingDataSourceProperties implements Serializable {
    private Map<String, DataSourceProperties> datasource;
    private ShardingRuleProperties shardingRule;
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

    public void setShardingRule(ShardingRuleProperties shardingRule) {
        this.shardingRule = shardingRule;
    }

    public MasterSlaveRuleProperties getMasterSlaveRule() {
        return masterSlaveRule;
    }

    public void setMasterSlaveRule(MasterSlaveRuleProperties masterSlaveRule) {
        this.masterSlaveRule = masterSlaveRule;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

