---
formatter: "@formatter:off"
layout: post
title: 定义枚举
subtitle: define-enum 
description: define-enum 
tags: [] 
date: 2021-01-07 15:18:55 +800 
version: 1.0
formatter: "@formatter:on"
---

# 定义枚举

## 简介

**枚举**（`Enum`）作为 `Java` 语言的一个基础类型，它可以避免**魔术数字/字符串**（`Magic Number/String`）和**限制输入**。

## 用法

声明一个实现了接口`IEnum`的枚举。

```java
package org.ifinal.finalframework.annotation.data;

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

> `YN` 是内建枚举。

既然是语言的基础组成，那为什么要单独说明呢？

## 为什么

具有`final`特性的枚举有以下特点：

* **可读性更高的Json**

```json
{
  "yn": 1,
  "ynName": "YES",
  "ynDesc": "有效"
}
```

* **可被持久化**

实现了`IEnum`接口的枚举在持久化时可使用`getCode()`的值进行持久化。

* **可用于接收参数**

实现了`IEnum`接口的枚举可直接收到**请求参数**（`@RequestParam`）或**请求体**（`@RequestBody`）中其`getCode()`对应的值。

> 在项目中应尽量避免使用`IEnum.getCode()`，甚至应该*禁止使用*。