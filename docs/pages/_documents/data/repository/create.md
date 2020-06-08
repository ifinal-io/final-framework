---
layout: post
title: Create
subtitle: 简单的Create
categories: [data,repository]
tags: [insert,replace,save]
menus:
    - data
    - repository
    - create
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Create

## 引言

`Create`(增加)是数据持久层的基本操作之一，业务中常见的业务场景有以下几种：

* **新增(`insert`)**：每次都新增数据，如日志。
* **保存(`save`)**：当数据存在时`update`，否则`insert`。
* **替换(`replace`)**：当数据存在时，先`delete`再`insert`。
* **忽略(`ignore`)**：当数据不存在时，`insert`，否则`ignore`。

根据上述场景并结合`SQL`，定义了以下几个`create`方法：

## Insert 

`insert`实现数据**新增**的逻辑，并兼容**忽略**的场景，作为可选条件从参数传入，支持的参数如下：

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `table` |  表名  |                     要插入的表名                     |
|   `view`    |  视图  |                可根据视图插入不同的列                |
|  `ignore`   |  忽略  | 当`ignore=true`时，生成的`SQL`为`INSERT IGNORE INTO` |
| `entities`  | 实体集 |                        实体集                        |

方法原型定义如下，提供了以上参数的多种重载函数：
```java
public interface Repository {
    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table 表名
     * @param view      视图,
     * @param ignore    是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities  实体集
     * @return 指插入数据所影响的行数
     */
    int insert(@Param("table") String table, @Param("view") Class<?> view, @Param("ignore") boolean ignore, @Param("list") Collection<T> entities);
}
```

```xml
<script>
    <trim prefix="INSERT">
        <if test="ignore">
            IGNORE
        </if>
        <trim prefix="INTO">
            ${table}
        </trim>
    </trim>
    <foreach collection="properties" item="property" open="(" separator="," close=")">
        <if test="property.hasView(view)">
            ${property.column}
        </if>
    </foreach>
    <trim prefix="VALUES">
        <foreach collection="list" item="entity" separator=",">
            <foreach collection="properties" item="property" open="(" separator="," close=")">
                <if test="property.hasView(view)">
                        #{entity.name}
                </if>
            </foreach>
        </foreach>
    </trim>
</script>
```

## Replace

`replace`实现当数据不存在时**新增（`insert`）**，否则**替换（`replace`）**，基于`SQL`中的`REPLACE INTO`实现，为*原子操作*。参数如下：

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `table` |  表名  |                     要插入的表名                     |
|   `view`    |  视图  |                可根据视图插入不同的列                |
| `entities`  | 实体集 |                        实体集                        |

> 因`REPLACE INTO`的原理是当**唯一约束**重复时，先执行`DELETE`再执行`INSERT`，因此，当`KEY`重复，其影响的行数为**2**。
> 使用`REAPLCE`时表应**有且有一个**唯一约束，否则可能会造成死锁。


方法原型定义如下，提供了以上参数的多种重载函数：
```java
public interface Repository {
    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table 表名
     * @param view      视图,
     * @param entities  实体集
     * @return 指插入数据所影响的行数
     */
    int replace(@Param("table") String table, @Param("view") Class<?> view, @Param("list") Collection<T> entities);
}
```


## Save

`save`实现当数据不存在时**新增（`insert`）**，否则**更新（`update`）**，基于`SQL`中的`INSERT INTO ON DUPLICATE KEY UPDATE`实现，为*原子操作*。参数如下：

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `table` |  表名  |                     要插入的表名                     |
|   `view`    |  视图  |                可根据视图插入不同的列                |
| `entities`  | 实体集 |                        实体集                        |



方法原型定义如下，提供了以上参数的多种重载函数：
```java
public interface Repository {
    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table 表名
     * @param view      视图,
     * @param entities  实体集
     * @return 指插入数据所影响的行数
     */
    int save(@Param("table") String table, @Param("view") Class<?> view, @Param("list") Collection<T> entities);
}
```