---
title: Json
subtitle: 数据传输的载体
categories: []
tags: []
layout: post
menus:
    - index
author: likly
date: 2019-03-16 18:03:50 +800
version: 1.0
---

# Json

## What

`Json`是目前最流行的数据传输载体。

在`Java`领域，比较常见的Json库有`jackson`、`gson`和`fastjson`，`Json`只是基于这三个库进行统一的`API`封装，并非**造轮子**，
使底级json库对开发者透明，降低项目与Json组件的耦合。

## Usage

* First，引入依赖

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-json-spring-boot-starter</artifactId>
    <version>{{ site.final.version }}</version>
</dependency>
```

* Second，使用[Json](json.md)对象API。

## Show

### Enum 增强

1. 支持在`Bean`对象中使用直接使用枚举值，序列化为其对应的`code`值。

2. 支持在`Bean`对象中添加枚举对应的描述性字段`xxxName`，序列化为期对应的`description`值。

> PS：枚举需要实现[IEnum](/final-data/final-data-core/src/main/java/org/finalframework/data/entity/enums/IEnum.java)接口。


