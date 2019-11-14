---
post: post
title: DateConverter
subtitle: 日期转换器
description: 接口调试日期格式难？常见的日期格式随心传，不出意外，我都认识！
layout: post
menus:
    - context
    - converter
    - date-converter
categories: [spring]
tags: [converter]
author: likly
date: 2019-03-17 20:42:41 +800
version: 1.0
---

# DateConverter

## What

[DateConverter](/org/finalframework/spring/web/converter/DateConverter.java)
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