package org.ifinal.finalframework.sharding.config;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BindingTableRegistry {
    private final Collection<String> bindingTableGroups ;

    public BindingTableRegistry(final Collection<String> bindingTableGroups) {

        this.bindingTableGroups = bindingTableGroups;
    }

    public BindingTableRegistry addBindingTable(final String... bindingTable) {

        this.bindingTableGroups.addAll(Arrays.asList(bindingTable));
        return this;
    }

    public BindingTableRegistry addAllBindingTables(final Collection<String> bindingTables) {

        this.bindingTableGroups.addAll(bindingTables);
        return this;
    }

}
