package org.ifinal.finalframework.sharding.config;

import lombok.AccessLevel;
import lombok.Getter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */

public class ShardingDataSourceRegistry {
    @Getter(AccessLevel.PACKAGE)
    private final Map<String, DataSource> dataSources = new HashMap<>();

    public ShardingDataSourceRegistry addDataSource(final String name, final DataSource dataSource) {

        this.dataSources.put(name, dataSource);
        return this;
    }


}
