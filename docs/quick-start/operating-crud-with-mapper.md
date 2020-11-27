---
layout: post
title: how-to-use-crud
subtitle: how-to-use-crud
description: how-to-use-crud
tags: []
menus:
    - how-to-use-crud
date: 2020-11-26 21:48:35 +800
version: 1.0
---

# how-to-use-crud



## Quick Start



## Import Dependency

* maven

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.boot/final-boot-starter-mybatis -->
<dependency>
    <groupId>org.ifinal.finalframework.boot</groupId>
    <artifactId>final-boot-starter-mybatis</artifactId>
    <version>${final.version}</version>
</dependency>
```

* gradle

```groovy
// https://mvnrepository.com/artifact/org.ifinal.finalframework.boot/final-boot-starter-mybatis
compile group: 'org.ifinal.finalframework.boot', name: 'final-boot-starter-mybatis', version: '${final.version}'
```



### Define Entity

```java
@Data
public class DataEntity extends IEntity<Long>{
    @PrimaryKey
    private Long id;
    private String key;
    private String value;
}
```

## Define Mapper

```java
@Mapper
public interface DataMapper extends AbsMapper<Long,DataEntity>{}
```

> 可使用`org.ifinal.finalframework.auto:final-auto-mybatis`在编译时自动生成对应`Mapper`。   

## Do CRUD

### Create

* 当需要插入数据时：

```java
dataMapper.insert(dataEntities...);
```

* 当需要插入到非默认表时：

```java
dataMapper.insert(tableName,dataEntities);
```

* 当且仅当数据不存在时插入，否则忽略（需要有唯一约束）：

```java
dataMapper.insert(true,dataEntities);
```
