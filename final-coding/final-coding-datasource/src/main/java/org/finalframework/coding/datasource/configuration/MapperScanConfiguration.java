package org.finalframework.coding.datasource.configuration;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 19:38:12
 * @since 1.0
 */
public class MapperScanConfiguration implements Serializable {

    private String[] basePackages;
    private String mapperLocations;
    private String transactionManager;
    private String sqlSessionFactory;
    private String sqlSessionTemplate;

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(String transactionManager) {
        this.transactionManager = transactionManager;
    }

    public String getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(String sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public String getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(String sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}

