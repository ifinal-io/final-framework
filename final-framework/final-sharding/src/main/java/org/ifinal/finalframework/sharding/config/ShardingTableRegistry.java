package org.ifinal.finalframework.sharding.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.ifinal.finalframework.annotation.sharding.ShardingTable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @see ShardingTable
 * @since 1.0.0
 */
public class ShardingTableRegistry {
    @Getter(AccessLevel.PACKAGE)
    private Collection<ShardingTableRegistration> tables = new ArrayList<>();


    public ShardingTableRegistration addShardingTableRule(final String logicTable, final String[] actualDataNodes) {

        ShardingTableRegistration registration = new ShardingTableRegistration(logicTable, actualDataNodes);
        tables.add(registration);
        return registration;
    }

    public ShardingTableRegistry addShardingTableRule(final ShardingTableRegistration table) {

        tables.add(table);
        return this;
    }


}
