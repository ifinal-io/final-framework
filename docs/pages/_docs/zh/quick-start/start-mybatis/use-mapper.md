---
formatter: off
layout: post 
title: 使用 Mapper
subtitle: how-to-use-crud 
description: how-to-use-crud 
tags: []
date: 2020-11-26 21:48:35 +800 
version: 1.0 
formatter: on
---

# 使用 Mapper

## 概述

`final-mybatis` 内置了功能强大的 `AbsMapper`，提供了丰富的 `CURD` 操作及灵活的查询条件。

## 如何定义 Mapper

### 自定义 Mapper

在 `.dao.mapper` 包下添加 `PersonMapper.java` ：

```java
package org.ifinal.finalframework.example.dao.mapper;

@Mapper
public interface PersonMapper extends AbsMapper<Long, Person> {

}
```

> 上述 `PersonMapper` 存在大量的模板代码，在不需要自定义**SQL**的前提下，可使用`final-auto-mybatis`在编译时生成。

### 使用 Auto Mybatis 编译生成

#### 添加依赖

* maven

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.auto/final-auto-mybatis -->
<dependency>
    <groupId>org.ifinal.finalframework.auto</groupId>
    <artifactId>final-auto-mybatis</artifactId>
    <version>{{ site.final.version }}</version>
    <scope>provided</scope>
</dependency>
```

#### 添加一个 java 文件

在源代码目录里添加一个java文件以触发`javac`，如 `package-info.java`。再次编译时就会生成实体类（`Entity`）所对应的 `EntityMapper`。

> `final-auto-mybatis` 生成 `*Mapper.java` 规则：
> * **Package**: `Entity.class.getPackage().replace('.entity','.dao.mapper')`。
> * **Name**: `Entity.class.getSimpleName() + 'Mapper'`。

## 如何使用 Mapper

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

