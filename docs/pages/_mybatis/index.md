---
post: post
title: Final Mybatis
subtitle: 简单、统一的CRUD操作接口
description: 提供统一、简单、明了的CRUD操作接口，并增强类型映射 
categories: []
tags: []
menus:
    - index
layout: post
author: likly
date: 2019-03-16 18:36:54 +800
version: 1.0
---

# Final Mybatis


## Questions

* 你是否为写`CRUD`模板代码而烦恼？

* 你是否由于添加（或删除）字段要重新生成`模板代码`（mybatis-generator）而头疼？

* 。。。

## What

**Final MyBatis** 

## Apis

提供以[`insert`](mapper/method/insert.md)、`update`、`delete`、`select`、`selectOne`、`selectIds`、`selectCount`命名的多种CURD方法，支持多种类型的方法重载。

### Insert

### Delete

### Update

### Select

### SelectOne

### SelectIds

### SelectCount

## 增强的类型

### 枚举类型

对于实现了[IEnum](/final-data/final-data-context/src/main/java/org/finalframework/data/entity/enums/IEnum.java)的枚举类型，
在持久化时，会将其持久化为其`IEnum#getCode()`所对应的值。

> 详情查看：[EnumTypeHandler](handler/enum-type-handler.md)

### Json类型

> 详情查看：[JsonTypeHandler](handler/json-type-handler.md)