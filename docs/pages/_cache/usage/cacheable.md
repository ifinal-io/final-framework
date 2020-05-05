---
title: Cacheable
subtitle: 缓存获取与设置
description: 通过简单的声明，即可增强目标方法，实现缓存的设置与获取。
categories: [redis]
layout: post
menus:
    - usage
    - cacheable
tags: []
toc: true
author: likly
date: 2019-03-15 22:27:55 +800
version: 1.0
---

# Cacheable

## What

**[`Cacheable`](/org/finalframework/cache/annotation/Cacheable.java)** 是一个基于注解(`Annotation`)
的缓存框架，能够为目标方法**Method**添加上缓存功能。

## Usage

在想要增加缓存功能的方法**Method**上添加声明**Cacheable**注解，配置相应的参数，即可为目标方法增加缓存功能。

**示例**：

* **需求**：将用户`User`的信息添加的缓存中，`key`的格式为`user:id`，仅当用户的状态`status`有效时（假设`status =1`时为有效）设置，
并设置缓存有效时间为`5分钟`。

* **Code**:
```java
public interface UserService{
    @Cacheable(key={"user:{#id}"},condition="{#result.status == 1}",ttl=5,timeunit=TimeUnit.MINUTE)
    User findById(Long id);
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


## Why

**“缓存”，一般是为了提高业务系统的响应效率，不涉及具体的业务逻辑。**

但是，为了给业务添加缓存功能，我们经常会写出如下的模板代码：

```java
public class DataService{
    @Resource
    private CacheService cacheService;
    
    public Data findDataById(Long id){
        // 获取KEY
        final String key = "cache:data:" + id;
        // 优先从缓存中获取
        final Data cache = cacheService.get(key);  
        // 缓存命中，直接返回
        if(cache != null) return cache;
        
        // 缓存未命中，查找逻辑
        final Data result = doFind(id);
        // 将查询结果添加在缓存中
        cacheService.set(key,result);
        // 返回结果
        return  result;
    }
}
```

在上述伪代码中，核心业务只有`final Data result = doFind(id);`这一行，其它都是为了提高该方法的查询效率而做的性能优化，即使用缓存。

我相信，这样的模板代码你写了N次，也`CV`了无数次，你也一定为这样的代码而感到苦恼，却无奈没有好的解决方法。。。

但是，从现在开始，上面的代码可以变成这样：

```java
public class DataService{
    @Cacheable(key={"cache:data:{#id}"})    
    public Data findDataById(Long id){
        return doFind(id);
    }
}
```

如此一来，缓存是不是简单了许多呢！
