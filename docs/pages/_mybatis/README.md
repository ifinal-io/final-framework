---
post: post
title: Final Mybatis
subtitle: 简单、统一的CRUD操作接口
description: 提供统一、简单、明了的CRUD操作接口，并增强类型映射 
categories: []
tags: []
permalink: /mybatis/
menus:
    - index
layout: page
author: likly
date: 2019-03-16 18:36:54 +800
version: 1.0
---

# Final Mybatis

## Questions

* 你是否为写`CRUD`模板代码而烦恼？

* 你是否由于添加（或删除）字段要重新生成`模板代码`（mybatis-generator）而头疼？

* 你是为查询过大的数据量为实现分页功能而焦头烂额?

* 。。。

## What

**Final MyBatis** 基于`Mybatis`，在Java编译期间，生成模板代码，并集成`PageHelper`实现全局分页控制。

1. 可根据`Entity`自动生成`EntityMapper.java`和`EntityMapper.xml`文件。

2. 全局的分页插件[PageableInterceptor](inteceptor/pageable-interceptor.md)

3. 增强的类型映射 
    
    3.1 `IEnum`类型的枚举映射为其声明的`#getCode()`值。详情查看[EnumTypeHandler](handler/enum-type-handler.md)
    
    3.2 复杂的引用类型`Object`或`Collection`映射为`json`。详情查看[JsonTypeHandler](handler/json-type-handler.md)


## Quick Start

### Import Dependency

* maven 

```xml
<dependencies>
    <dependency>
        <groupId>YOUR_GROUP_ID</groupId>
        <artifactId>YOUR_ENTITY_ARTIFACT_ID</artifactId>
        <version>YOUR_ENTITY_VERSION</version>
    </dependency>
    <dependency>
        <groupId>org.finalframework</groupId>
        <artifactId>final-mybatis-spring-boot-starter</artifactId>
        <version>{{site.final.version}}</version>
    </dependency>
    <dependency>
        <groupId>org.finalframework</groupId>
        <artifactId>final-coding-query</artifactId>
        <version>{{site.final.version}}</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.finalframework</groupId>
        <artifactId>final-coding-mapper</artifactId>
        <version>{{site.final.version}}</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Compile & Run

* maven

```shell
mvn clean compile
```
