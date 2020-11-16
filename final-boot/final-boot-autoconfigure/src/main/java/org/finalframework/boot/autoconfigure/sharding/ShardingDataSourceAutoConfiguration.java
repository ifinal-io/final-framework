package org.finalframework.boot.autoconfigure.sharding;


import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-11 11:10:40
 * @since 1.0
 */
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnMissingBean(DataSource.class)
@EnableConfigurationProperties(ShardingDataSourceProperties.class)
public class ShardingDataSourceAutoConfiguration {
    private final ShardingDataSourceProperties properties;

    public ShardingDataSourceAutoConfiguration(ShardingDataSourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DataSource shardingDataSource() throws SQLException {
        return properties.build();
    }
}

