---
title: Cache
subtitle: 基于注解的缓存框架
description: 缓存不是业务，它的存在是提高业务，不要让模板代码成为你工作效率的中硌脚石。
layout: post
permalink: /cache/index.html
menus:
    - index
categories: []
tags: []
author: likly
date: 2019-03-15 22:19:57 +800
version: 1.0
---

# Cache

## What

基于注解(`Annotation`)声明式的缓存框架，旨在简化程序中的缓存逻辑，让缓存在业务中更加透明化。

`Cache`实现在`Method`的执行生命周期中，增加目标方法的功能，如：

* 在方法执行之前获取分布式锁，避免重复执行。
* 统计方法的执行次数、成功次数和失败次数。
* 在方法执行之前，优先从缓存中获取获取数据。
* 在方法执行之后，将方法的返回值全部或部分值添加到缓存中。
* ……

## Guide

### Import

添加依赖：

* maven

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-cache-spring-boot-starter</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

### Declared

在目标方法`Method`或参数 `Parameter` 上添加缓存注解。

```java
public interface CacheService {
    
    @Cacheable(key="key:{#id}")
    String cacheMethod(Long id);
}
```


## CutPoint

`Cache`实现在方法的生命周期中织入相应的缓存逻辑，[CutPoint](../_spring/aop/cutpoint.md)将执行生命周期拆分为
`Before`、`After`、`AfterReturnning`和`AfterThrowing`四个阶段，其中`After`包含`AfterReturning`和`AfterThrowing`。

## Annotation

{% include cache/annotations.html %}

### Cacheable

[Cacheable](usage/cacheable.md)，为目标方法增加缓存能力。

1. 在方法执行之前，查询缓存命中，如果命中，则直接使用缓存数据；
2. 在方法执行之后，将业务方法的返回值设置到缓存中。

### CacheLock

[CacheLock](usage/cache-lock.md)，为目标方法增加分布式锁的功能。

1. 在方法执行之前，尝试获取缓存锁，只有成功获取锁的方法才能继续执行。
2. 在方法执行之后，释放获取到的缓存锁。

### CacheDel

[CacheDel](usage/cachedel.md)，为目标方法增加缓存删除功能。

1. 在方法执行之后或之后，将指定的缓存删除。

### CachePut

[CachePut](usage/cacheput.md)，为目标方法增加缓存设置功能。

1. 在方法执行之后，将指定的值设置到缓存中去。