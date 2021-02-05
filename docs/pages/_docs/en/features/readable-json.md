---
formatter: "@formatter:off"
layout: post 
title: 增强的JSON 
subtitle: json 
description: json 
tags: []
date: 2020-11-11 15:44:32 +800 
version: 1.0
formatter: "@formatter:on"
---

# Readable JSON

## Date

Define a bean with data type property.

```java

@Data
public class DateBean {

    private Date date;

    private LocalDateTime localDateTime;

}
```

the json will get:

```json
{
  "date": 1605059845585,
  "dateFormat": "2020-11-11 09:57:25",
  "localDateTime": 1605059845603,
  "localDateTimeFormat": "2020-11-11 09:57:25"
}
```

## Enum

Define a bean with enum type property.

```java

@Data
static class EnumBean {

    private YN yn = YN.YES;

}
```

the json will get like this:

```json
{
  "yn": 1,
  "ynName": "YES",
  "ynDesc": "有效"
}
```

> 枚举需要实现接口：`IEnum`。    