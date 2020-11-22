---
layout: post
title: hotswap
subtitle: hotswap
description: hotswap
tags: []
menus:
    - hotswap
date: 2020-11-22 22:02:42 +800
version: 1.0
---
    
# 热更新

## What

**热更新**是一种在不停机的情况下，修改*java*的技术。

## Why

## How

熟悉`Arthas`的人应该知道，通过组合命令`jad`->`mc`->`redefine`可实现不停机的情况下改变类的行为。    

![Arthas热更新流程](http://assets.processon.com/chart_image/5fba6fb90791293c5429cf66.png)

## Then

既然技术上可以实现，那如果能够脱离`command`改用人机交互更友好的`GUI`来操作岂不是更好！

![Hotswap](images/hotswap.png)


* 定义`HotSwapApiController`如下：

```java
@RestController
public class HotswapApiController {

    @GetMapping("/api/java/hotswap")
    public String hotswap() {
        return "hello hotswap!";
    }

}
```

* 访问``

```json
{
    "status": 0,
    "description": "success",
    "code": "0",
    "message": "success",
    "data": "hello hotswap!",
    "trace": "20b1d6b1-70d2-4c7f-bdbf-f67106c100c8",
    "timestamp": 1606055367413,
    "duration": 0.032,
    "address": "127.0.0.1:8080",
    "locale": "zh_CN",
    "timeZone": "Asia/Shanghai",
    "success": true
}
```

* Jad并Hotswap

```java
/*
 * Decompiled with CFR.
 */
package org.finalframework.example.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotswapApiController {
    @GetMapping(value={"/api/java/hotswap"})
    public String hotswap() {
        return "hello hotswap done!";
    }
}

```

* 重新访问

```json
{
    "status": 0,
    "description": "success",
    "code": "0",
    "message": "success",
    "data": "hello hotswap done!",
    "trace": "04a6553d-d922-4c30-a0e8-85cc32f9c8f4",
    "timestamp": 1606055566802,
    "duration": 0.002,
    "address": "127.0.0.1:8080",
    "locale": "zh_CN",
    "timeZone": "Asia/Shanghai",
    "success": true
}
```




## See Also

* [CFR:提供`jad`技术支持](https://github.com/leibnitz27/cfr)
* [Arthas:提供*热更新*原理](https://arthas.aliyun.com/doc/index.html)