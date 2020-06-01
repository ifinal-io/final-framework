package org.finalframework.test.datasource;


import org.finalframework.data.datasource.DataSourceAutoConfigurationProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 17:26:59
 * @since 1.0
 */
//@Configuration
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class ShardingDataSourceAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "final.sharding")
    public DataSourceAutoConfigurationProperties shardingDataSourceAutoConfigurationProperties() {
        return new DataSourceAutoConfigurationProperties();
    }

    @Bean
    public DataSource dataSource(DataSourceAutoConfigurationProperties shardingDataSourceAutoConfigurationProperties) throws SQLException {
        return shardingDataSourceAutoConfigurationProperties.build();
    }
}

