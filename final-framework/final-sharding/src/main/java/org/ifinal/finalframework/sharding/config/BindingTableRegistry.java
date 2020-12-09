package org.ifinal.finalframework.sharding.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BindingTableRegistry {
    private final Collection<String> bindingTableGroups = new ArrayList<>();

    public BindingTableRegistry addBindingTable(String... bindingTable) {
        this.bindingTableGroups.addAll(Arrays.asList(bindingTable));
        return this;
    }

    public BindingTableRegistry addAllBindingTables(Collection<String> bindingTables) {
        this.bindingTableGroups.addAll(bindingTables);
        return this;
    }

}
