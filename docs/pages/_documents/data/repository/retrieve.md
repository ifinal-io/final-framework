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
| `table` |  表名  |                     要查询的表名                     |
|   `view`    |  视图  |                可根据视图查询不同的列                |
|    `ids`    |  ID 集合 |                                                  |
|   `query`  | 查询   |                                                    |


```java
public interface Repository {
    /**
     * 根据 {@link ID} 集合或 {@link Query} 查询
     *
     * @param table 表名
     * @param view      视图
     * @param ids       要查询的IDS
     * @param query     查询条件
     */
    List<T> select(@Param("table") String table, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);

}
```

## SelectOne

`selectOne()`与`select()`功能类型，限制了查询的数量，会在生成`sql`时添加后缀`limit 1`。

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `table` |  表名  |                     要查询的表名                     |
|   `view`    |  视图  |                可根据视图查询不同的列                |
|    `id`    |  ID |                                                  |
|   `query`  | 查询   |                                                    |

```java
public interface Repository{
    /**
     * 返回符合查询 {@link ID} 或 {@link Query} 的一个结果，当找不到时返回 {@code null}
     *
     * @param table 表名
     * @param view  视图
     * @param id    ID
     * @param query query
     * @return 符合查询 {@link ID} 或 {@link Query} 的一个结果
     */
    T selectOne(@Param("table") String table, @Param("view") Class<?> view, @Param("id") ID id, @Param("query") Query query);
}
```

## SelectIds

`selectIds()`用于仅需要返回数据行**主键**的查询。

```java
public interface Repository{
    /**
     * 返回符合查询条件 {@link Query} 的主键集合 {@link ID}
     *
     * @param table 表名
     * @param query query
     * @return 符合查询条件 {@link Query} 的主键集合 {@link ID}
     */
    List<ID> selectIds(@Param("table") String table, @Param("query") Query query);
}
```

## SelectCount

`selectCount()`用于统计符合条件的结果集数量。

* Param

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `table` |  表名  |                     要查询的表名                     |
|    `ids`    |  ID集合 |                                                  |
|   `query`  | 查询   |                                                    |

* Definition

```java
public interface Repository{
    /**
     * 返回符合查询条件 {@link Query}的结果集的大小
     *
     * @param table 表名
     * @param query query
     * @return 符合查询条件 {@link Query}的结果集的大小
     */
    long selectCount(@Param("table") String table, @Param("ids") Collection<ID> ids, @Param("query") Query query);

}
```

## isExists