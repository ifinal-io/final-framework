---
title: Annotation
subtitle: ORM映射
description: 使用Annotation构建实现与数据库的映射关系
layout: post
categories: []
tags: []
menus:
    - data
    - orm
    - annotation
author: likly
date: 2019-03-25 22:07:16 +800
version: 1.0
---

# Annotation

## IEntity & IEnum

为了方便处理，分别定义了业务中实体类`Entity`和枚举常量`Enum`的的抽象接口
[IEntity](/org/finalframework/data/annotation/IEntity.java)
和[IEnum](/org/finalframework/data/annotation/IEnum.java)。

并且，对于实体`Entity`对象，还声明了一个基础的实现类[AbsEntity](/org/finalframework/data/entity/AbsEntity.java),
在其中声明了一些常用的属性，如`created`、`lastModified`和`yn`。

再之，在[Coding](../../coding/README.md)模块中，还加入了一些**编译校验**，如：

* `Enum`枚举必须实现`IEnum`接口。
* `Entity`实体类必须实现`Serializable`接口，并且声明`serialVersionUID`属性，其格式为`private static final long serialVersionUID = -3500516904657883963L;`

## Annotations

提供了以下**ORM**注释，可通过不同的组合，实现多样的功能。

{% include framework/data/annotations.html %}

### @Table

[@Table](/org/finalframework/data/annotation/Table.java)
注释用于当实体类与数据库表之间不能通过统一的名称转换器[NameConverter](/org/finalframework/data/mapping/converter/NameConverter.java)
映射时，强制指定实现类与数据表的映射关系。

> PS：内置的名称转换器
> * [SimpleNameConverter](/org/finalframework/data/mapping/converter/SimpleNameConverter.java): 实体类名与数据表名一样。
> * [CameHump2UnderlineNameConverter](/org/finalframework/data/mapping/converter/CameHump2UnderlineNameConverter.java): 
>实体类名与数据表名之间通过驼峰转下划线的规则映射。

### @Transient

[@Transient](/org/finalframework/data/annotation/Transient.java)
注释用于标记一个实体类或类的成员不需要被编译器处理。

### @Column

[@Column](/org/finalframework/data/annotation/Column.java)
注释作为最基础的数据列映射注释，用于声明类的成员为需要映射到数据库中的某一个数据列上，该注释是可选的，在默认情况下，实体类的成员属性都会映射成数据库中的列。

### @Default

[@Default](/org/finalframework/data/annotation/Default.java)作用于当数据列有
默认值，不需要在`insert`时写入数据时，声明了`@Default`的属性不会生成到`insert`方法中。
