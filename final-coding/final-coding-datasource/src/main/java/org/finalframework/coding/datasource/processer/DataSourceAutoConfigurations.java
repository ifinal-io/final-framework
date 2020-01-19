package org.finalframework.coding.datasource.processer;

import org.finalframework.coding.annotation.Template;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:16
 * @since 1.0
 */
@Template("datasource/dataSourceAutoConfiguration2.vm")
@SuppressWarnings("unused")
public class DataSourceAutoConfigurations implements Serializable {
    private final String packageName;
    private final String name;
    private final String properties;
    private final String[] basePackages;
    private final String mapperLocations;
    private final String prefix;
    private final String dataSource;
    private final String transactionManager;
    private final String sqlSessionFactory;
    private final String sqlSessionTemplate;
    private final boolean primary;

    private DataSourceAutoConfigurations(Builder builder) {
        this.packageName = builder.packageName;
        this.name = builder.name;
        this.properties = builder.properties;
        this.basePackages = builder.basePackages;
        this.mapperLocations = String.join(",", builder.mapperLocations);
        this.prefix = builder.prefix;
        this.dataSource = builder.dataSource;
        this.transactionManager = builder.transactionManager;
        this.sqlSessionFactory = builder.sqlSessionFactory;
        this.sqlSessionTemplate = builder.sqlSessionTemplate;
        this.primary = builder.primary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPackage() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getProperties() {
        return properties;
    }

    public String getBasePackages() {
        return Arrays.stream(basePackages).map(it -> String.format("\"%s\"", it)).collect(Collectors.joining(","));
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getTransactionManager() {
        return transactionManager;
    }

    public String getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public String getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public boolean isPrimary() {
        return primary;
    }

    public static class Builder implements org.finalframework.core.Builder<DataSourceAutoConfigurations> {
        private String packageName;
        private String name;
        private String properties;
        private String[] basePackages;
        private String[] mapperLocations;
        private String prefix;
        private String dataSource;
        private String transactionManager;
        private String sqlSessionFactory;
        private String sqlSessionTemplate;
        private boolean primary = false;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder properties(String properties) {
            this.properties = properties;
            return this;
        }

        public Builder basePackages(String[] basePackages) {
            this.basePackages = basePackages;
            return this;
        }

        public Builder mapperLocations(String[] mapperLocations) {
            this.mapperLocations = mapperLocations;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder dataSource(String dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public Builder transactionManager(String transactionManager) {
            this.transactionManager = transactionManager;
            return this;
        }

        public Builder sqlSessionFactory(String sqlSessionFactory) {
            this.sqlSessionFactory = sqlSessionFactory;
            return this;
        }

        public Builder sqlSessionTemplate(String sqlSessionTemplate) {
            this.sqlSessionTemplate = sqlSessionTemplate;
            return this;
        }

        public Builder primary(boolean primary) {
            this.primary = primary;
            return this;
        }

        @Override
        public DataSourceAutoConfigurations build() {
            return new DataSourceAutoConfigurations(this);
        }
    }
}
