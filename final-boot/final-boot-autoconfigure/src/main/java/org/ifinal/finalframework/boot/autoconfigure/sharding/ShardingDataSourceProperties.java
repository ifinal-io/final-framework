package org.ifinal.finalframework.boot.autoconfigure.sharding;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = ShardingDataSourceProperties.DEFAULT_DATASOURCE_PREFIX)
public class ShardingDataSourceProperties {

    static final String DEFAULT_DATASOURCE_PREFIX = "final.sharding";

    private Map<String, DataSourceProperties> datasource;

}

