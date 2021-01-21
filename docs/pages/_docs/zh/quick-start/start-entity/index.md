---
formatter: "@formatter:off"
layout: post
title: 数据实体
subtitle: how-to-define-an-entity
description: how-to-define-an-entity
tags: []
date: 2020-11-27 12:54:17 +800
version: 1.0
formatter: "@formatter:on"
---

# 数据实体

## 简介

`final-entity` 定义了**对象关系映射** `ORM`(**Object/Relational Mapping**)的规则。

除了支持基本的数据类型，还扩展了对枚举和复杂对象的映射支持。

通过本节学习，您将得到如下收获：

* 如何定义实体
* 如何定义枚举

## 快速开始

在开始之前，请确保您已经搭建好了一个`final`工程。如果您尚未搭建有`final`工程，请查阅: [开始 Final](../start-final.md)。

### 导入依赖

* maven

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot-starter-entity -->
<dependency>
    <groupId>org.ifinal.finalframework.starter</groupId>
    <artifactId>final-boot-starter-entity</artifactId>
    <version>{{ site.final.version }}</version>
</dependency>
```

* gradle

```groovy
// https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot-starter-entity
compile group: 'org.ifinal.finalframework.starter', name: 'final-boot-starter-entity', version: '{{ site.final.version }}'
```

### 用法

* [定义实体](define-entity.md)
* [定义枚举](define-enum.md)
