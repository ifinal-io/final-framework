---
layout: page
title: Update
subtitle: update
categories: []
tags: []
menus:
    - mapper
    - method
    - update
author: likly
date: 2019-10-15 17:12:24 +800
version: 1.0
---

# Update

## Methods

### Prototype

```java
/**
 * 更新数据并返回影响的行数
 *
 * @param tableName 表名
 * @param view      视图
 * @param entity    实体，值不为 {@code null}时，忽略 {@code update} 的值
 * @param update    更新，仅当 {@code entity}为空时有效
 * @param selective 有选择的，值为{@code true}时，不更新值为 {@code null}的属性。
 * @param ids       要更新的IDS
 * @param query     更新条件
 * @return 更新数据后影响的行数
 */
int update(@Param("tableName") String tableName, @Param("view") Class<?> view,
           @Param("entity") T entity, @Param("update") Update update, @Param("selective") boolean selective,
           @Param("ids") Collection<ID> ids, @Param("query") Query query);
```

### Overload


## Mapper
