package org.ifinalframework.data.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import org.junit.jupiter.api.Test;

import jakarta.annotation.Resource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBootApplication
class DruidDataSourceFactoryTest {

    private final DruidDataSourceFactory druidDataSourceFactory = new DruidDataSourceFactory();

    @Resource
    private Environment environment;
    @Resource
    private DataSourceProperties properties;

    @Test
    void create() throws SQLException {
        final DruidDataSource dataSource = druidDataSourceFactory.create(properties, environment, "spring.datasource");
        assertEquals("SELECT 1", dataSource.getValidationQuery());
    }
}