---
layout: post
title: Final
subtitle: 以简化开发为目标
categories: []
tags: []
permalink: /documents/
menus:
    - index
author: likly
date: 2020-03-22 21:30:09 +800
version: 1.0
---

# Final

## What's Final

**Final** 是对项目中常用到的*库*一次简单的封装，在此基础上，再提供**简单而又实用**的功能，从而达到更**高效**的开发。

## What's Final Do



## Stronger Json

`Json`在默认的实现之上，增强了对一些特殊类型的处理，如`Enum`和`Date`，`Json`在序列化的时候，不仅序列了其原有的属性，还为其增加了扩展属性，
从而更好的来描述`Enum`和`Date`等。

### Enum

1. 对于实现了[IEnum](data/enum.md)的`Enum`，在`Json`序列化的时候，会增加扩展属性`name`和`desc`来增强对`code`的描述，极大的减少沟通的成本。
2. 在序列化枚举`Class`时，序列化为枚举常量集的对象描述，更多请查看[EnumConverter](data/converter/enum-converter.md)
    ```json
    [
        {
            "code":1,
            "name":"YES",
            "ordinal":0,
            "desc":"有效"
        },
        {
            "code":0,
            "name":"NO",
            "ordinal":1,
            "desc":"无效"
        },
        {
            "code":-1,
            "name":"DELETED",
            "ordinal":2,
            "desc":"删除"
        }
    ]
    ```

### Date

对于`Date`类型的属性，如`java.util.Date`和`java.time.LocalDateTime`，`Json`的序列化时，除了会序列会其对应的**时间戳**，
还是为其扩展一个后缀`format`的属性，其值为`date`的`yyyy-MM-dd hh:mm:ss`格式化后的值。

```json
{
    "created":1584885769474,
    "createdFormat":"2020-03-22 22:02:49",
    "yn":1,
    "ynName":"YES",
    "ynDescription":"有效"
}
```

## Simple Repository

`Final` 提供了强大的`Data Access Object` 数据访问接口对象[Repository](/final-data/final-data-context/src/main/java/org/finalframework/data/repository/Repository.java) 
包含了大量**简单又实用**的`CURD`入口。

* `insert`: 
* `replace`: 
* `save`: 
* `update`: 
* `delete`: 
* `select`: 
* `selectOne`: 
* `selectIds`: 
* `selectCount`: 
* `isExists`: 

## Powerful Criterion

[Criterion](pages/_documents/data/criterion.md)提供了丰富了查询标准，除了常用的`null`，`comptare`,`between`,`in`和`like`，还加入了对`json`的支持。