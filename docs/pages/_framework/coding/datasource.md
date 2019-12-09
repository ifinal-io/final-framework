---
layout: post
title: DataSource
subtitle: 数据源配置
description: 快速实现多数据源配置，同时支持`application`配置提示。
categories: []
tags: []
menus:
    - coding
    - datasource
author: likly
date: 2019-11-04 10:50:20 +800
version: 1.0
---

# DataSource


[DataSource](/final-coding/final-coding-datasource/src/main/java/org/finalframework/coding/datasource/DataSource.java)
实现在编译时生成[DataSourceProperties](/final-coding/final-coding-datasource/src/main/resources/template/datasource/dataSourceProperties.vm)
和[DataSourceAutoConfiguration](/final-coding/final-coding-datasource/src/main/resources/template/datasource/dataSourceAutoConfiguration.vm)。

## Usage

### Import Dependency

* maven

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-coding-datasource</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

### Create DataSource

```java
@DataSource(prefix="spring.datasource",basePackages="org.finalframework.test.dao.mapper",primary=true)
public class MyDataSource{

}
```

### Compile 

* maven 

```shell
mvn clean compile
```

### Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&verifyServerCertificate=false&useSSL=false&requireSSL=false
    username: root
    password: root
```

## Template

### DataSourceProperties

生成的`DataSourceProerties`会被`Spring AutoConfiguration`再次处理，可生成与`spring.datasource`相同的配置文件，能够在`application`配置文件中给出相应的提示功能。

```
package ${dataSourceProperties.packageName};

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "${dataSourceProperties.prefix}")
public class ${dataSourceProperties.name} extends DataSourceProperties {
}
```

### DataSourceAutoConfiguration

`DataSourceAutoConfiguration`结合`mybatis`的`MapperScan`，实现多数据源的配置。

```
#set($dataSource = $dataSourceAutoConfiguration)
package ${dataSource.package};

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
#if($dataSource.primary)
import org.springframework.context.annotation.Primary;
#end
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(${dataSource.properties}.class)
@MapperScan(basePackages = {${dataSource.basePackages}}, sqlSessionTemplateRef = "${dataSource.sqlSessionTemplate}", sqlSessionFactoryRef = "${dataSource.sqlSessionFactory}")
public class ${dataSource.name} {

    public static final String TRANSACTION_MANAGER = "${dataSource.transactionManager}";


    private final ${dataSource.properties} properties;

    public ${dataSource.name}(${dataSource.properties} properties) {
        this.properties = properties;
    }


    @Bean
    #if($dataSource.primary)
    @Primary
    #end
    public DataSource ${dataSource.dataSource}() {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    #if($dataSource.primary)
    @Primary
    #end
    public DataSourceTransactionManager ${dataSource.transactionManager}(@Qualifier("${dataSource.dataSource}") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    #if($dataSource.primary)
    @Primary
    #end
    public SqlSessionFactory ${dataSource.sqlSessionFactory}(@Qualifier("${dataSource.dataSource}") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        #if($!dataSource.mapperLocations != "")
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("${dataSource.mapperLocations}"));
        #end
        return bean.getObject();
    }

    @Bean
    #if($dataSource.primary)
    @Primary
    #end
    public SqlSessionTemplate ${dataSource.sqlSessionTemplate}(@Qualifier("${dataSource.sqlSessionFactory}") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

```