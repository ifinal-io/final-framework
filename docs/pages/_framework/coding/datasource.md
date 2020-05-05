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


[DataSource](/final-coding/final-coding-datasource/src/main/java/org/finalframework/coding/datasource/annotation/DataSource.java)
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
@DataSource(prefix = "final.sharding", value = {"ds0"})
@ShardingRule(tables = {Person.class})
public interface ShardingDataSource {
}
```

### Compile 

* maven 

```shell
mvn clean compile
```

### Configuration

```yaml
final:
  sharding:
    datasource:
      # 数据源配置
      ds0:
        url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&verifyServerCertificate=false&useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: Aa000000
        driver-class-name: com.mysql.cj.jdbc.Driver
    sharding-rule:
      tables:
        person:
          # 逻辑表名
          logic-table: person
          # 物理节点
          actual-data-nodes: ds0.person_${0..1}
          # 表分片策略
          table-sharding-strategy:
            inline:
              sharding-column: age
              algorithm-expression: person_${age % 2}
          # 数据源分片策略
          database-sharding-strategy:
            inline:
              sharding-column: age
              algorithm-expression: ds${age % 2}
```
