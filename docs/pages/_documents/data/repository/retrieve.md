---
layout: post
title: Retrieve
subtitle: 丰富的Query方法
categories: [data,repository]
tags: [select,selectOne,selectIds,selectCount,isExists]
menus:
    - data
    - repository
    - retrieve
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Retrieve

## Select


|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `tableName` |  表名  |                     要查询的表名                     |
|   `view`    |  视图  |                可根据视图查询不同的列                |
|    `ids`    |  ID 集合 |                                                  |
|   `query`  | 查询   |                                                    |


```java
public interface Repository {
    /**
     * 根据 {@link ID} 集合或 {@link Query} 查询
     *
     * @param tableName 表名
     * @param view      视图
     * @param ids       要查询的IDS
     * @param query     查询条件
     */
    List<T> select(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);

}
```

## SelectOne

## SelectIds

## SelectCount

## isExists