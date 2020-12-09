package org.ifinal.finalframework.sharding.config;

import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.ifinal.finalframework.sharding.annotation.ShardingType;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @since 1.0.0
 */
public class ShardingAlgorithmRegistry {

    /**
     * @see org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm
     */
    private static final String INLINE_ALGORITHM_EXPRESSION = "algorithm-expression";
    private final Map<String, ShardingSphereAlgorithmConfiguration> shardingAlgorithms = new HashMap<>();

    public ShardingAlgorithmRegistry addShardingAlgorithm(String name, ShardingType type, Properties properties) {
        shardingAlgorithms.put(name, new ShardingSphereAlgorithmConfiguration(type.name(), properties));
        return this;
    }

    /**
     * @param name       name
     * @param expression expression
     * @return registry
     * @see org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm
     */
    public ShardingAlgorithmRegistry addInlineShardingAlgorithm(String name, String expression) {
        Properties properties = new Properties();
        properties.put(INLINE_ALGORITHM_EXPRESSION, expression);
        return addShardingAlgorithm(name, ShardingType.INLINE, properties);
    }


}

