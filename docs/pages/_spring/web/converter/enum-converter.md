---
post: post
title: EnumConverter
subtitle: 枚举转换器
layout: post
categories: []
tags: []
menus:
    - web
    - converter
    - enum-converter
author: likly
date: 2019-03-17 21:11:24 +800
version: 1.0
---

# EnumConverter

## What

[EnumConverter](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/converter/EnumConverter.java)
枚举转换器实现将表单请求中的枚举常量值转换成对应的枚举类型。

## Usage

* 定义枚举并实现 [IEnum](/final-data/final-data-context/src/main/java/org/finalframework/data/entity/enums/IEnum.java)接口。

```java
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1),
    /**
     * 无效
     */
    NO(0);
    /**
     * 枚举码
     */
    private final Integer code;


    YN(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
```
* 在Controller的参数上直接使用枚举接收参数

```java
@RestController
public class EnumController{
    @GetMapping("/enums")
    public YN yn(YN yn){
        return yn;
    }
}
```