---
title: Cache
subtitle: 基于注解的缓存框架
description: 缓存不是业务，它的存在是提高业务，不要让模板代码成为你工作效率的中硌脚石。
layout: post
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



## Annotation

### Cacheable

[Cacheable](annotation/cacheable.md)，为目标方法增加缓存能力。
 
1. 在方法执行之前，查询缓存命中，如果命中，则直接使用缓存数据；
2. 在方法执行之后，将业务方法的返回值设置到缓存中。
 
### CacheLock

[CacheLock](annotation/cachelock.md)，为目标方法增加分布式锁的功能。

1. 在方法执行之前，尝试获取缓存锁，只有成功获取锁的方法才能继续执行。
2. 在方法执行之后，释放获取到的缓存锁。

### CacheDel

[CacheDel](annotation/cachedel.md)，为目标方法增加缓存删除功能。

1. 在方法执行之后或之后，将指定的缓存删除。

### CachePut

[CachePut](annotation/cacheput.md)，为目标方法增加缓存设置功能。

1. 在方法执行之后，将指定的值设置到缓存中去。