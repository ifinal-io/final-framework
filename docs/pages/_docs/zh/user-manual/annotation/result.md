---
formatter: off
layout: post
title: 统一结果集 
subtitle: result 
description: result 
tags: [] 
date: 2021-01-10 23:01:32 +800 
version: 1.0
issues: 
    - https://github.com/likly/final-frameworks/issues/10
formatter: on
---

# 统一结果集

## 简介

为方便结果处理，`final`定义了统一的**结果集**（`Result`），支持**分页**（`Pagination`）

## Result

| 属性          | 类型      | 含义     | 备注 |
| :------------: | :--------: | :-------: | :---: |
| `status`      | `Integer` | 状态     |      |
| `description` | `String`  | 状态描述 |      |
|    `code`    |           `String`           |   业务码   |      |
|  `message`   |           `String`           | 业务码描述 |      |
|    `data`    |             `T`              |  业务数据  |      |
| `pagination` |         `Pagination`         |  分页信息  |      |
|   `trace`    |           `String`           | 业务trace  |      |
| `timestamp`  |            `Long`            | 响应时间戳 |      |
|  `duration`  |          `Duration`          |  业务耗时  |      |
|  `address`   |           `String`           |  服务地址  |      |
|     `ip`     |           `String`           |   服务IP   |      |
|   `locale`   |           `Locale`           |    地区    |      |
|  `timeZone`  |          `TimeZone`          |    时区    |      |
|  `operator`  |           `IUser`            |   操作人   |      |
|    `view`    |           `Class`            |    视图    |      |
| `exception`  | `Class<? extends Throwable>` |    异常    |      |

* Json

```json
{
  "status": 0,
  "description": "success",
  "code": "0",
  "message": "success",
  "data": "hello final!",
  "trace": "7aba435f-69d2-4c44-a944-315107623a92",
  "timestamp": 1605063263491,
  "duration": 0.063,
  "address": "127.0.0.1:80",
  "locale": "en",
  "timeZone": "Asia/Shanghai",
  "success": true
}
```

## Pagination

| 属性          | 类型      | 含义     | 备注 |
| :------------: | :--------: | :-------: | :---: |
| `page`      | `Integer` | 当前页     |      |
| `size` | `Integer`  | 页容量 |      |
| `pages` | `Integer`  | 总页数 |      |
| `total` | `Long`  | 总记录数 |      |
| `firstPage` | `Boolean`  | 是否首页 |      |
| `lastPage` | `Boolean`  | 是否末页 |      |

* Json

```json
{
  "status": 0,
  "description": "success",
  "code": "0",
  "message": "success",
  "data": [
    {}
  ],
  "pagination": {
    "page": 1,
    "size": 1,
    "pages": 1,
    "total": 1,
    "firstPage": true,
    "lastPage": 1
  },
  "trace": "7aba435f-69d2-4c44-a944-315107623a92",
  "timestamp": 1605063263491,
  "duration": 0.063,
  "address": "127.0.0.1:80",
  "locale": "en",
  "timeZone": "Asia/Shanghai",
  "success": true
}
```