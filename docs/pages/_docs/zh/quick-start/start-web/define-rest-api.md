---
formatter: "@formatter:off"
layout: post 
title: 定义Rest接口
subtitle: define-rest-api-in-controller 
description: define-rest-api-in-controller 
tags: []
date: 2020-12-06 19:23:12 +800 
version: 1.0 
formatter: "@formatter:on"
---

# 定义Rest接口

## 定义 RestController

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    public String hello(String word) {
        return "hello " + word + "!";
    }

}
```

## 访问接口

访问上述定义的接口 `http://localhost:8080/hello?word=final!` 将得到以下结果：

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
