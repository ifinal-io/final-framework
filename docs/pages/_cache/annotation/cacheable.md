---
title: Cacheable
subtitle: cacheable
description: 通过简单的声明，即可增强目标方法，实现缓存的设置与获取。
categories: [redis]
layout: post
menus:
    - annotation
    - cacheable
tags: []
toc: true
author: likly
date: 2019-03-15 22:27:55 +800
version: 1.0
---

# Cacheable

## Why

在你的工作之中，你是否经常需要为了提高系统的执行效率，降低数据库的访问压力，提升系统的并发能力，需要将一些比较**热门**的数据设置到缓存中，
从而来满足上述需求的一些指标。

为了达到上述的缓存目的，你是否又经常`CV`些模板代码，这样的操作方式是否令你感到**厌恶**，甚至有些**头疼**。

如何减少`CV`这种操作，同时还能达到数据的缓存目的，并且还支持一些可配置的选项，以满足不同的业务需求……

## What's Your Want

不确定你想要的解决方案是什么样的，但下面是我想到的一种解决方案：

**通过简单的声明式方式来增强目标`Method`的功能，以达到数据缓存的目的。**

**功能点**：

* 在**Method**调用之前，检测缓存**Cache**中是否有与`key`和`field`相对应的数据，如果有，则直接将缓存中的数据返回。
* 在**Method**调用之后，将方法的返回值设置到缓存`Cache`相应的`key`和`field`所描述的空间。
* 数据并不一定需要缓存，可配置在数据满足一定条件`conditon`的前提下再设置缓存。
* 可配置**过期时间**和**有效时间**。

## This's Mine

**Cacheable**：可缓存的注解（`Annotation`），通过在目标**Method**上声明该注解，达到数据的缓存目的。

**配置项**：

|   配置项    | 是否可选 |   类型   |         默认值          |          备注           |
| :---------: | :--: | :------: | :---------------------: | :---------------------: |
|    `key`    |  `N`   | `String[]` |           `无`          |         `缓存KEY`         |
|   `field`   |  Y   | `String[]` |           `[]`           |         缓存域          |
| `delimiter` |  Y   |  `String`  |           `:`           | 缓存KEY和缓存域的分隔符 |
| `condition` |  Y   |  `String`  |           `无`           |        缓存条件         |
|  `expire`   |  Y   |  `String`  |           `无`           |        过期时间         |
|    `ttl`    |  Y   |   `Long`   |           `-1`           |        有效时间         |
| `timeunit`  |  Y   |   `TimeUnit`   | `TimeUnit.MILLISECONDS` |      有效时间单位       |


## How to Use It

在需要增强的**Method**上声明**Cacheable**注解，并指明缓存的`key`和`field`(可选)，即可实现在目标方法之前，优先获取缓存中的数据。
如果命中缓存，则将缓存中的数据直接返回，减少目标**Method**方法的调用次数，提高数据访问效率；未命中，则继续调用目标**Method**，
并在调用完成之后，将返回值设置到缓存**Cache**中。

**需求**：
> 将用户`User`的信息添加的缓存中，`key`的格式为`user:id`，仅当用户的状态`status`有效时（假设`status =1`时为有效）设置，
并设置缓存有效时间为`5分钟`。

**示例**：

```java
public interface UserService{
    @Cacheable(key={"user:{#id}"},condition="{#result.status == 1}",ttl=5,timeunit=TimeUnit.MINUTE)
    User findById(Long id);
}
```

## How it to Work

```java
public class UserServiceImpl implements UserService{
    
    User findById(Long id){
        // 在方法调用之前先从Cache中获取数据
        final Object cacheValue = Cache.get(Cacheable.key(),Cacheable.field());
        if(cacheValue != null){
            return cacheValue;
        }
        
        // 方法调用
        final Object returnValue = method.invode();
        
        // 在方法调用之后，将方法的返回值设置到缓存中
        Ccahe.set(Cacheable.key(),Cacheable.field(),returnValue);
        
        return returnValue;
        
    }
}
```
