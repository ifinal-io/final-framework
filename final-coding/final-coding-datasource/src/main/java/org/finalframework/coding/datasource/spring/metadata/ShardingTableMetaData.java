package org.finalframework.coding.datasource.spring.metadata;


import org.finalframework.data.datasource.TableRuleProperties;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-06 17:21:03
 * @since 1.0
 */
public class ShardingTableMetaData extends ConfigurationMetadata {

    private final String prefix;
    private final String table;

    public ShardingTableMetaData(String prefix, String table) {
        this.prefix = prefix;
        this.table = table;
        this.init();
    }

    private void init() {

        this.add(ItemMetadata.newGroup(prefix + "." + table, TableRuleProperties.class.getCanonicalName(), TableRuleProperties.class.getCanonicalName(), null));

        this.logicTable();
        this.actualDataNodes();
        this.merge(new SharingStrategyMetaData(prefix + "." + table + ".database-sharding-strategy"));
        this.merge(new SharingStrategyMetaData(prefix + "." + table + ".table-sharding-strategy"));

    }

    private void logicTable() {
        add(ItemMetadata.newProperty(prefix, table + ".logicTable",
                String.class.getCanonicalName(), TableRuleProperties.class.getCanonicalName(),
                null, "逻辑表名", null, null));
    }

    private void actualDataNodes() {
        this.add(ItemMetadata.newProperty(prefix, table + ".actualDataNodes",
                String.class.getCanonicalName(), TableRuleProperties.class.getCanonicalName(),
                null, "物理节点", null, null));

    }

}

