---
title: CacheValue
subtitle: 把缓存中的值注入到参数中
layout: post
categories: []
tags: []
menus:
    - annotation
    - cachevalue
author: likly
date: 2019-03-23 16:08:55 +800
version: 1.0
---

# CacheValue

## What

[`CacheValue`](/final-cache/final-cache-core/src/main/java/org/finalframework/cache/annotation/CacheValue.java)注解实现在方法执行之前，
将其所描述的缓存区域中的值赋予被标记的参数`Parameter`。

## Usage

```java
public interface LoginService{
    User login(String account,String password,@CacheValue(key={"login:{#acount}"}) Long loginCount);
}
```