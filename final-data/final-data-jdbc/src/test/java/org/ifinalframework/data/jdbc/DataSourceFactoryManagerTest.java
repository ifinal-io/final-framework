package org.ifinalframework.data.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.ClassUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DataSourceFactoryManagerTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
class DataSourceFactoryManagerTest {

    @Test
    @SneakyThrows
    void getDataSourceFactory() {
        DataSourceFactoryManager manager = new DataSourceFactoryManager();

        final DataSourceFactory<? extends DataSource> dataSourceFactory = manager.getDataSourceFactory(null);
        Assertions.assertNotNull(dataSourceFactory);


        Assertions.assertTrue(ClassUtils.isLambdaClass(dataSourceFactory.getClass()));
        final DataSourceProperties properties = new DataSourceProperties();
        properties.afterPropertiesSet();
        dataSourceFactory.create(properties, new StandardEnvironment(), "spring.datasource");


        DataSourceFactory<? extends DataSource> factory = manager.getDataSourceFactory(DruidDataSource.class);
        Assertions.assertTrue(factory instanceof DruidDataSourceFactory);

        factory = manager.getDataSourceFactory(DataSource.class);
        assertFalse(factory instanceof DruidDataSourceFactory);

    }
}