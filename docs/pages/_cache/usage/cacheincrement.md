---
post: post
title: CacheIncrement
subtitle: 缓存统计
layout: post
categories: []
tags: []
menus:
    - usage
    - cacheincrement
author: likly
date: 2019-03-24 12:39:08 +800
version: 1.0
---

# CacheIncrement

## What

[CacheIncrement](/org/finalframework/cache/annotation/CacheIncrement.java)
实现在方法的执行生命周期内，统计次数。如目标方法的执行次数、执行成功次数、执行失败次数等。

## Usage

```java
public interface LoginService{
    /**
    * 在登录失败后，记录该账号的登录次数
    */
    @CacheIncrement(key={"login:failure:{#account}"},cutPoint=Order.AfterThrowing)
    User login(String account,String password);
}
```

## Options

|   配置项    | 是否可选 |   类型   |         默认值          |          备注           |
| :---------: | :--: | :------: | :---------------------: | :---------------------: |
|    `key`    |  `N`   | `String[]` |           `无`          |         `缓存KEY`         |
|   `field`   |  Y   | `String[]` |           `[]`           |         缓存域          |
| `delimiter` |  Y   |  `String`  |           `:`           | 缓存KEY和缓存域的分隔符 |
| `condition` |  Y   |  `String`  |           `无`           |        缓存条件         |
|  `expire`   |  Y   |  `String`  |           `无`           |        过期时间         |
|    `ttl`    |  Y   |   `Long`   |           `-1`           |        有效时间         |
| `timeunit`  |  Y   |   `TimeUnit`   | `TimeUnit.MILLISECONDS` |      有效时间单位       |

