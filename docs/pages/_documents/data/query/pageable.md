---
layout: post
title: Pageable
subtitle: 统一的分页处理
categories: [data,query]
tags: [pageable]
menus:
    - data
    - query
    - pageable
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Pageable

[Pageable](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Pageable.java)定义了基本的分页参数`page`和`size`。
实现了该接口的查询对象都会被默认的[分页插件](/mybatis/inteceptor/pageable-interceptor)拦截而进行分页查询。

## Definition

```java
public interface Pageable {
    /**
     * 返回分页页码
     *
     * @return 分页页码
     */
    @Nullable
    Integer getPage();

    /**
     * 返回页面容量
     *
     * @return 页面容量
     */
    @Nullable
    Integer getSize();

    /**
     * 是否进行Count统计
     *
     * @return Count统计
     */
    @Nullable
    default Boolean getCount() {
        return Boolean.TRUE;
    }

}

```

## Tks

* [Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper): 感谢`Mybatis-PageHelper`提供的分页插件。