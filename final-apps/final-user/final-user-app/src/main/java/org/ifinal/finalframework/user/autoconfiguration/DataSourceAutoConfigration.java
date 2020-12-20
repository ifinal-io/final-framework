package org.ifinal.finalframework.user.autoconfiguration;

import java.util.Map;
import javax.sql.DataSource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.ifinal.finalframework.amp.client.AmpService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSourceAutoConfigration.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DataSourceAutoConfigration {

    @DubboReference
    private AmpService ampService;

    @Bean
    public DataSource dataSource() {

        Map<String, org.ifinal.finalframework.amp.entity.DataSource> dataSources = ampService.getDataSources("final-user", "final-user");

        org.ifinal.finalframework.amp.entity.DataSource dataSource = dataSources.get("default");

        DataSourceProperties properties = new DataSourceProperties();

        properties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        properties.setUsername(dataSource.getUsername());
        properties.setPassword(dataSource.getPassword());
        properties.setUrl(String
            .format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false",
                dataSource.getHost(), dataSource.getPort(), dataSource.getSchema()));

        return properties.initializeDataSourceBuilder().build();
    }

}
