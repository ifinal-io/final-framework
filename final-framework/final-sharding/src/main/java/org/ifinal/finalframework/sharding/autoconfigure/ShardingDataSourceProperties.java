package org.ifinal.finalframework.sharding.autoconfigure;

import java.util.Map;
import java.util.Properties;
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

    private boolean enable = true;

    private Map<String, DataSourceProperties> datasource;

    private Properties props;

}

