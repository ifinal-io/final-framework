---
layout: post
title: Select
subtitle: select
categories: []
tags: []
menus:
    - mapper
    - method
    - select
author: likly
date: 2019-10-15 17:12:24 +800
version: 1.0
---

# Select

## Methods

### Prototype

```java
/**
 * 根据 {@link ID} 集合或 {@link Query} 查询
 *
 * @param tableName 表名
 * @param view      视图
 * @param ids       要查询的IDS
 * @param query     查询条件
 */
List<T> select(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);
```

### Overload


## Mapper
