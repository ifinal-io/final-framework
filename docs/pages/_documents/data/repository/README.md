---
layout: page
title: Repository
subtitle: 简单实用的CRUD
categories: [data]
tags: [repository]
permalink: /data/repository/
author: likly
menus: 
    - data
    - repository
    - index
date: 2020-03-22 22:34:07 +800
version: 1.0
---

# Repository

[Repository](/org/finalframework/data/repository/Repository.java) 

## Create

### Insert 

`insert`是`SQL`中的基本组成之一，实现将数据插入`DB`中，`insert`支持的参数如下：

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `tableName` |  表名  |                     要插入的表名                     |
|   `view`    |  视图  |                可根据视图插入不同的列                |
|  `ignore`   |  忽略  | 当`ignore=true`时，生成的`SQL`为`INSERT IGNORE INTO` |
| `entities`  | 实体集 |                        实体集                        |

方法原型定义如下，提供了以上参数的多种重载函数：
```java
public interface Repository {
    /**
     * 批量插入数据并返回影响的行数
     *
     * @param tableName 表名
     * @param view      视图,
     * @param ignore    是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities  实体集
     * @return 指插入数据所影响的行数
     */
    @Deprecated
    int insert(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("ignore") boolean ignore, @Param("list") Collection<T> entities);
}
```

### Replace

`replace` 与 `insert` 功能雷同，但又有一丝区别，`insert` 当唯一约束存在时，重复数据插入会出错或者不插入（`INSERT IGNORE`）,
而`replace`会先`delete`原有的数据，再`insert`新的数据，因此当`key`重复时，`replace`影响的行数为**2**.

### Save

## Update


## Retrieve

### Select

## Delete
