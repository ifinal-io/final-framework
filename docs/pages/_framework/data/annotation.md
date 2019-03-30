---
title: Annotation
subtitle: ORM映射
description: 使用Annotation构建实现与数据库的映射关系
layout: post
categories: []
tags: []
menus:
    - data
    - annotation
author: likly
date: 2019-03-25 22:07:16 +800
version: 1.0
---

# Annotation

## Class

### Entity

[Entity](/final-data/final-data-annotation/src/main/java/org/finalframework/data/annotation/Entity.java)
注解用于标记目标类为一个实体类，被标记的类会在编译时生成其对应的`QEntity`对象。

### Table

[Table](/final-data/final-data-annotation/src/main/java/org/finalframework/data/annotation/Table.java)
注解用于构建实体类与数据库表之间的映射关系，当实体类名称与表名称一致或存在一定映射规则时，可不使用。

## Field

### Column