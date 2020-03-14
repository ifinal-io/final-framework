package org.finalframework.coding.datasource.spring.metadata;


import org.finalframework.data.datasource.InlineShardingStrategyProperties;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-06 18:38:12
 * @since 1.0
 */
public class InlineSharingStrategyMetaData extends ConfigurationMetadata {
    private final String prefix;

    public InlineSharingStrategyMetaData(String prefix) {
        this.prefix = prefix;
        this.init();
    }

    private void init() {

        this.add(ItemMetadata.newGroup(prefix, InlineShardingStrategyProperties.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(), null));


        this.shardingColumn();
        this.algorithmExpression();
    }

    private void shardingColumn() {
        this.add(ItemMetadata.newProperty(prefix, "shardingColumn",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, "分片列", null, null));
    }

    private void algorithmExpression() {
        this.add(ItemMetadata.newProperty(prefix, "algorithmExpression",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, "分片算法表达式", null, null));
    }
}

