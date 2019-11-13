---
title: PageableInterceptor
subtitle: 分页拦截器
description: 分页拦截器，实现全局的分页功能。
layout: post
categories: []
tags: []
menus:
    - interceptor
    - pageable-interceptor
author: likly
date: 2019-03-24 23:31:22 +800
version: 1.0
---

# PageableInterceptor

## What

[PageableInterceptor](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/inteceptor/PageableInterceptor.java)
分页拦截器插件实现在`Mapper`执行之前进行分页功能。

## How

`PageableInterceptor`分页拦截器的原理是当`MappedStatement`的参数（或参数列表中的某一个）实现了[Pageable](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Pageable.java)
接口，并且执行的是`query`方法，那么该方法将会被执行分页拦截。是否分页取决于`Pageable#getPage()`和`Pageable#getSize()`的值都存在。

## Usage

1. 使用[Query](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Query.java)查询；
2. 自定义查询参数，并实现[Pageable](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Pageable.java)接口,
如[PageQuery](/final-data/final-data-context/src/main/java/org/finalframework/data/query/PageQuery.java)

> 目前使用的分页插件为[PageHelper](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/inteceptor/PageHelperPageableInterceptor.java),
访问[GitHub](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/README_zh.md)。
>
> 感谢作者[LiuZh](https://github.com/abel533)的贡献。