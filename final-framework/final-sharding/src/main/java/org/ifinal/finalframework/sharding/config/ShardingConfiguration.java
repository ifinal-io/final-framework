package org.ifinal.finalframework.sharding.config;

import java.util.Collection;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Builder;
import lombok.Getter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
public class ShardingConfiguration {

    private final Map<String, DataSource> datasource;

    private final Collection<ShardingTableRegistration> tables;

    private final Collection<ShardingAlgorithmRegistration> shardingAlgorithms;

}
