package org.ifinal.finalframework.sharding.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BroadcastTableRegistry {
    private final Collection<String> broadcastTables = new ArrayList<>();

    public BroadcastTableRegistry addBroadcastTable(String... broadcastTable) {
        this.broadcastTables.addAll(Arrays.asList(broadcastTable));
        return this;
    }

    public BroadcastTableRegistry addBroadcastTables(Collection<String> broadcastTables) {
        this.broadcastTables.addAll(broadcastTables);
        return this;
    }

}
