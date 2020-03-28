---
layout: page
title: Delete
subtitle: Delete
categories: [data,repository]
tags: [delete]
menus:
    - data
    - repository
    - insert
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Delete
  
`delete`实现数据的**删除**操作。

* 支持的参数如下:

|    参数     |  含义  |                         说明                         |
| :---------: | :----: | :--------------------------------------------------: |
| `tableName` |  表名  |                     要删除的表名                     |
|   `ids`    |  ID信息  |                要删除的主键集合              |
| `query`  | Query |                        删除的条件                        |


> PS: 当参数`ids`和`query`同时存在时，忽略`query`。

* 方法原型定义如下：

```java
public interface Repository {
/**
     * 删除符合条件的数据并返回影响的行数
     *
     * @param tableName 表名
     * @param ids       IDS
     * @param query     条件
     * @return 删除符合条件的数据所影响的行数
     */
    int delete(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") Query query);
}
```