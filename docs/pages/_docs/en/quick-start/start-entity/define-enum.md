---
formatter: "@formatter:off"
layout: post
title: Define Enum 
subtitle: define-enum 
description: define-enum 
tags: [] 
date: 2021-01-07 15:18:55 +800 
version: 1.0
formatter: "@formatter:on"
---

# Define Enum

## What

`Enum` is a base type of `java` which can avoid the `magic number(or String)` and `limit input`.

## How

### Declare an Enum which implements IEnum

Declare one enum and implements the interface of `IEnum`.

```java

@Getter
@AllArgsConstructor
@Description("有效标记")
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1, "有效"),
    /**
     * 无效
     */
    NO(0, "无效");

    /**
     * 枚举码
     */
    private final Integer code;

    private final String desc;

}
```

> The `YN` is a build-in enum.

## Why

The enum of `IEnum` is stronger more than normal enum.

* Could be saved to datasource use the value of `getCode()`.
* Could be parsed from the value of `getCode()` in `Json` and `RequestParam`.
* More readable in json.

```json
{
  "yn": 1,
  "ynName": "YES",
  "ynDesc": "有效"
}
```