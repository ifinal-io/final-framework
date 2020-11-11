---
layout: post
title: 统一的结果集
subtitle: result
description: result
tags: []
menus:
    - result
date: 2020-11-11 15:46:17 +800
version: 1.0
---
    
# 统一的结果集  

你在`@RestController`中定义的方法：

```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(String word) {
        return "hello " + word + "!";
    }
}
```

你在访问`/hello?word=final`时，得到的结果：

```json
{
    "status":0,
    "description":"success",
    "code":"0",
    "message":"success",
    "data":"hello final!",
    "trace":"7aba435f-69d2-4c44-a944-315107623a92",
    "timestamp":1605063263491,
    "duration":0.063,
    "address":"127.0.0.1:80",
    "locale":"en",
    "timeZone":"Asia/Shanghai",
    "success":true
}
```  