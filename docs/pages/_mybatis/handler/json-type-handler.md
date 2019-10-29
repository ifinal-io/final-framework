---
title: JsonTypeHandler
subtitle: json-type-handler
description: json-type-handler
layout: post
categories: []
tags: []
menus:
    - handler
    - json-type-handler
author: likly
date: 2019-03-30 15:48:02 +800
version: 1.0
---

# JsonTypeHandler

## What

[JsonTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonTypeHandler.java)
实现引用对象与`json`的映射。

## Usage

在需要使用`JsonTypeHandler`的属性`Field`上添加[JsonColumn](/final-data/final-data-annotation/src/main/java/org/finalframework/data/annotation/JsonColumn.java)注解即可。

已实现的`JsonTypeHandler`如下：

* JsonTypeHandler
    * [JsonObjectTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonObjectTypeHandler.java)
    * [JsonListTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonListTypeHandler.java)
    * [JsonSetTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonSetTypeHandler.java)
* JsonBlobTypeHandler
    * [JsonObjectTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonObjectBlobTypeHandler.java)
    * [JsonListTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonListTypeHandler.java)
    * [JsonSetTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/JsonSetTypeHandler.java)
    