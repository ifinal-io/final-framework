package org.finalframework.coding.datasource.spring.metadata;


import org.finalframework.data.datasource.ShardingRuleProperties;
import org.finalframework.data.datasource.TableRuleProperties;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-06 17:29:08
 * @since 1.0
 */
public class ShardingRuleMetaData extends ConfigurationMetadata {
    private final String prefix;
    private final List<String> tables;

    public ShardingRuleMetaData(String prefix, List<String> tables) {
        this.prefix = prefix;
        this.tables = tables;
        this.init();
    }

    private void init() {

        this.add(ItemMetadata.newGroup(prefix, ShardingRuleProperties.class.getCanonicalName(), ShardingRuleProperties.class.getCanonicalName(), null));


        for (String table : tables) {
            String prefix = this.prefix + ".tables";
            String tableName = table.substring(0, 1).toLowerCase() + table.substring(1);
            this.merge(new ShardingTableMetaData(prefix, tableName));
        }

        this.bindingTables();
        this.broadcastTables();

        this.merge(new SharingStrategyMetaData(prefix + ".default-database-sharding-strategy"));
        this.merge(new SharingStrategyMetaData(prefix + ".default-table-sharding-strategy"));
    }


    /**
     * {
     * "name": "final.sharding.sharding-rule.binding-tables",
     * "type": "java.util.List<java.lang.String>",
     * "sourceType": "org.finalframework.data.datasource.ShardingRuleProperties"
     * }
     */
    private void bindingTables() {

        this.add(ItemMetadata.newProperty(prefix, "bindingTables",
                "java.util.List<java.lang.String>", ShardingRuleProperties.class.getCanonicalName(),
                null, null, null, null));
    }

    /**
     * {
     * "name": "final.sharding.sharding-rule.broadcast-tables",
     * "type": "java.util.List<java.lang.String>",
     * "sourceType": "org.finalframework.data.datasource.ShardingRuleProperties"
     * }
     */
    private void broadcastTables() {

        this.add(ItemMetadata.newProperty(prefix, "broadcastTables",
                "java.util.List<java.lang.String>", ShardingRuleProperties.class.getCanonicalName(),
                null, null, null, null));
    }
}

