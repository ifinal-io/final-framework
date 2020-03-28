---
layout: page
title: SeletCount
subtitle: select-count
categories: []
tags: []
menus:
    - mapper
    - method
    - select-count
author: likly
date: 2019-10-15 16:22:13 +800
version: 1.0
---

# SelectCount

## Methods

### Origin

```java
/**
 * 返回符合查询条件 {@link Query}的结果集的大小
 *
 * @param tableName 表名
 * @param query     query
 * @return 符合查询条件 {@link Query}的结果集的大小
 */
long selectCount(@Param("tableName") String tableName, @Param("query") Query query);
```
### Overload

```java
long selectCount();

long selectCount(String tableName);

long selectCount(Queryable query);

long selectCount(Query query);

long selectCount(String tableName, Queryable query);
```

## Mapper

```xml
<select id="selectCount" resultType="java.lang.Long">
    <trim prefix="SELECT COUNT(*) FROM">
        <include refid="sql-table"/>
        <include refid="sql-query"/>
    </trim>
</select>
```