---
formatter: off
layout: post 
title: define-rest-api-in-controller 
subtitle: define-rest-api-in-controller 
description: define-rest-api-in-controller 
tags: []
menus:
    - quick-start
    - start-web
    - define-rest-api
date: 2020-12-06 19:23:12 +800 
version: 1.0 
formatter: on
---

# define-rest-api-in-controller

## Import Dependency

* maven

```xml

<dependencies>
    <dependency>
        <groupId>org.ifinal.finalframework.boot</groupId>
        <artifactId>final-boot-starter-web</artifactId>
        <version>${final.version}</version>
    </dependency>
</dependencies>
```

## Define a Rest Api

```java

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    public String hello(String word) {
        return "hello " + word + "!";
    }

}
```

## Visit The Api

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

## See Also

* [Global Result Wrapper](../../features/global-result-wrapper.md)