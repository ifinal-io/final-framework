---
layout: post
title: how-to-define-an-entity
subtitle: how-to-define-an-entity
description: how-to-define-an-entity
tags: []
menus:
    - how-to-define-an-entity
date: 2020-11-27 12:54:17 +800
version: 1.0
---
    
# how-to-define-an-entity

## What

`Entity`通常代表数据实体，在`final-framework`的世界里，它表示与数据库表的映射关系。

## How

### Import Dependency

* maven

```xml
    <dependencies>
        <dependency>
            <groupId>org.ifinal.finalframework.boot</groupId>
            <artifactId>final-boot-starter-entity</artifactId>
        </dependency>
    </dependencies>
```    

### Define Entity Bean

定义一个实现了`IEntity`接口的`Bean`。

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long>{
    @AutoIncr
    @PrimaryKey
    private Long id;
    private String name;
    private Integer age;
}
```

### Declare @Column Annotation (Optional)

当实体属性(`Field`)名称与数据表列名(`Column`)不相同时，可使用`@Column("column")`注解显示声明。

如：

```java
@Column("my_name")
private String name
```