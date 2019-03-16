---
title: cachelock
subtitle: cachelock
description: 使用缓存锁，快速实现分布式任务锁，防止任务重复执行。
layout: post
categories: []
tags: []
menus: 
    - annotation
    - cachelock
author: likly
date: 2019-03-16 15:15:27 +800
version: 1.0
---

# CacheLock
## Why

由于业务的需求日益复杂，导致目前大多数系统不仅是多线程的，还有多实例的，JVM提供的线程锁已经不能满足在分布式系统的任务同步。
为了解决在分布式系统的同步问题，有很多解决文案，最常用的就是**分布式锁**机制了。

而在分布式锁的应用上，目前比较常用的是使用`Redis`缓存锁，如同[`Cacheable`](cacheable.md)一样，也是些`CV`模板代码了。

那么，如何从系统的代码是将这些模板代码给移除掉呢？这将是接下来要讨论的问题。

## What's Your Want

在分布式锁的应用时，锁的**获取**与**释放**应满足以下几点：

* 锁的**获取**与**释放**必须是原子操作（`Redis`已经满足）；
* 当且仅当**锁不存在时**才能获取成功；
* 当且仅当**释放锁**与**获取锁**是同一调用者时才能释放成功；
* 锁要有**过期时间**，避免无锁；

在满足上述分布式锁的基础之一，支持以下可选项：

* 重试机制，可配置重试次数与间隔

## This's Mine

**CacheLock**:缓存锁注解（`Annotation`），在目标**Method**上声明该注解，即可给目标方法添加上分布式锁的功能。

**配置项**：

|   配置项    |    类型    | 是否可选 |     含义     |         默认值          |
| :---------: | :--------: | :------: | :----------: | :---------------------: |
|    `key`    |  String[]  |    N     |  缓存锁Key   |           无            |
|   `value`   |   String   |    Y     | 缓存锁Value  |         `UUID`          |
| `delimiter` |   String   |    Y     |    分隔符    |           `:`           |
| `condition` |   String   |    Y     |     条件     |           无            |
|    `ttl`    |    Long    |    Y     |   有效时间   |        -1,不过期        |
| `timeunit`  | `TimeUnit` |    Y     | 有效时间单位 | `TimeUnit.MILLISECONDS` |
|   `retry`   |    Int     |    Y     |   重试次数   |       `0，不重试`       |
|   `sleep`   |    Long    |    Y     |   重试间隔   |       `1000`(ms)        |



## How it to Use

在需要使用分布式锁的方法**Method**上声明`CacheLock`注解，并配置相应的`key`和`value`，以及过期时间`ttl`及重试次数`retry`和重试间隔`sleep`，
即可给目标方法**Method**增加分页式锁的功能。

**需求**：

> 在订单服务`OrderService`的下单方法`order(request)`中，对于同一个下单请求`request`在同一时刻有且仅能有一个下单动作，避免造成用户重复下单的问题。

**示例**：

```java
public interface OrderService{
    @CacheLock(key={"order:{#request.id}"},ttl=5,timeunit=TimeUnit.MINUTES)
    Order order(Request request);
}
```

## How it to Work

```java
public class OrderServiceImpl implements OrderSerivce{
    Order order(Request request){
        // 尝试获取缓存锁，如果获取成功，则继续执行目标方法，否则会抛出获取缓存锁异常。
        Cache.lock(CacheLock.key(),CacheLock.value(),CacheLock.ttl(),CacheLock.timeunit());
        
        // 下单业务
        final Order order = try2Order(request);
        
        // 下单完成之后，释放获取的缓存锁
        Cache.unlock(CacheLock.key(),CacheLock.value());
        
        return request;
        
    }
}
```