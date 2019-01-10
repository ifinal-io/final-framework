package com.ilikly.finalframework.coding.plugins.datasource.processer;

import com.ilikly.finalframework.coding.annotation.Template;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:16
 * @since 1.0
 */
@Template("datasource/datasource.ftl")
@SuppressWarnings("unused")
public class DataSourceModel implements Serializable {
    private final String packageName;
    private final String name;
    private final String[] basePackages;
    private final String mapperLocations;
    private final String prefix;
    private final String dataSource;
    private final String transactionManager;
    private final String sqlSessionFactory;
    private final String sqlSessionTemplate;


    private DataSourceModel(Builder builder) {
        this.packageName = builder.packageName;
        this.name = builder.name;
        this.basePackages = builder.basePackages;
        this.mapperLocations = String.join(",", builder.mapperLocations);
        this.prefix = builder.prefix;
        this.dataSource = builder.dataSource;
        this.transactionManager = builder.transactionManager;
        this.sqlSessionFactory = builder.sqlSessionFactory;
        this.sqlSessionTemplate = builder.sqlSessionTemplate;
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

    public static class Builder implements com.ilikly.finalframework.core.Builder<DataSourceModel> {
        private String packageName;
        private String name;
        private String[] basePackages;
        private String[] mapperLocations;
        private String prefix;
        private String dataSource;
        private String transactionManager;
        private String sqlSessionFactory;
        private String sqlSessionTemplate;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

        @Override
        public DataSourceModel build() {
            return new DataSourceModel(this);
        }
    }
}
