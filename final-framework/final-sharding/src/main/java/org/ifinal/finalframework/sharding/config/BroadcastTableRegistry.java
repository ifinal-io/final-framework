package org.ifinal.finalframework.sharding.config;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BroadcastTableRegistry {
    private final Collection<String> broadcastTables;

    public BroadcastTableRegistry(final Collection<String> broadcastTables) {

        this.broadcastTables = broadcastTables;
    }

    public BroadcastTableRegistry addBroadcastTable(final String... broadcastTable) {

        this.broadcastTables.addAll(Arrays.asList(broadcastTable));
        return this;
    }

    public BroadcastTableRegistry addBroadcastTables(final Collection<String> broadcastTables) {

        this.broadcastTables.addAll(broadcastTables);
        return this;
    }

}
