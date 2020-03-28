---
layout: page
title: Criteria
subtitle: 简单易用的组合
categories: [data,query]
tags: [criteria]
menus:
    - data
    - query
    - criteria
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Criteria

`Criteria`是`Criterion`的复数，实现将单一的`Criterion`根据需要进行组合，实现复杂的查询。

## And

```java
Criteria.and(Criterion... criterion)
```

## Or


```java
Criteria.or(Criterion... criterion)
```