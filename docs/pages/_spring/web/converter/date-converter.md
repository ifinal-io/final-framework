---
post: post
title: DateConverter
subtitle: 日期转换器
layout: post
menus:
    - web
    - converter
    - date-converter
categories: []
tags: []
author: likly
date: 2019-03-17 20:42:41 +800
version: 1.0
---

# DateConverter

## What

[DateConverter](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/converter/DateConverter.java)
实现将表单请求中的`String`格式的日期转换成`java.util.Date`类型。

支持以下格式：

* `yyyy-MM-dd HH:mm:ss`
* `yyyy-MM-dd`
* `yyyyMMdd HH:mm:ss`
* `yyyyMMdd`
* `yyyy/MM/dd HH:mm:ss`
* `yyyy/MM/dd`
* `yyyyMMddHHmmss`

## Usage

无感的、透明的，无需多余配置。