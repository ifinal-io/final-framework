---
formatter: "@formatter:off"
layout: post
title: 定义实体
subtitle: how-to-define-an-entity
description: how-to-define-an-entity
tags: []
date: 2020-11-27 12:54:17 +800
version: 1.0
formatter: "@formatter:on"
---

# 定义实体

## 简介

`Entity`通常代表数据实体，在`final-framework`的世界里，它表示与数据库表的映射关系。

## 用法

### 定义实体对象

定义一个实现了`IEntity`接口的`Bean`。

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long> {

    @AutoIncr
    @PrimaryKey
    private Long id;

    private String name;

    private Integer age;

}
```

> `final` 通过接口 `IEntity` 来识别实体类（`Entity`）。

### 自定义表名 (可选)

当实体名称(`Class.getSimpleName()`)与与数据库表名(`Table`)不相同时，可使用`@Table("table")`注解显示声明。

如：`Person`->`t_person`

```java
import lombok.Data;

@Data
@Table("t_person")
public class Person implements IEntity<Long> {

}
```

### 自定义列名 (可选)

当实体属性(`Field`)名称与数据表列名(`Column`)不相同时，可使用`@Column("column")`注解显示声明。

如：`name` -> `my_name`

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long> {

    @Column("my_name")
    private String name;

}
```

### 声明 Json 列 (可选)

当实体属性(`Field`)需要以`JSON`格式存在到数据库中时，可使用`@Json`注解显示声明

如：`List<String> tags` -> `["tag"]`

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long> {

    @Json
    private List<String> tags;

}
```

### 声明非数据列 (可选)

当实体属性(`Field`)不参与数据库列(`Column`)映射时，可使用`@Transient`注解显示声明。

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long> {

    @Transient
    private String notColumn;

}
```

## See Also

* [对象关系映射](../../user-manual/annotation/orm.md)
* [如何使用CRUD](how-to-use-crud.md)