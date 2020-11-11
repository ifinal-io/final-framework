---
layout: post
title: 增强的JSON
subtitle: json
description: json
tags: []
menus:
    - json
date: 2020-11-11 15:44:32 +800
version: 1.0
---
    
# 增强的JSON

## 日期

你定义包含日期类型的的Bean：

```java
@Data
public class DateBean {
    private Date date;
    private LocalDateTime localDateTime;
}
```

你得到序列化后的JSON：

```json
{
    "date":1605059845585,
    "dateFormat":"2020-11-11 09:57:25",
    "localDateTime":1605059845603,
    "localDateTimeFormat":"2020-11-11 09:57:25"
}
```

## 枚举

你定义包含**枚举类型的Bean：

```java
@Data
static class EnumBean{
    private YN yn = YN.YES;
}
```

你得到序列化后的JSON：

```json
{
    "yn":1,
    "ynName":"YES",
    "ynDesc":"有效"
}
```
    