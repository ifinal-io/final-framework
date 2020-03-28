---
title: README
subtitle: Action 概述
description: 简述 action 
layout: page
categories: []
tags: []
menus:
    - action
    - readme
author: likly
date: 2019-03-30 10:43:34 +800
version: 1.0
---

# README

## Usage

* First，引入依赖：

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-monitor-spring-boot-starter</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

* Second，声明`OperationAction`

```java
@RestController
public class IndexController{
    
    @GetMapping
    @OperationAction("访问首页")
    public String index(){
        return "Hello";
    }
}
```

* Now，启动`Application`并访问`http://localhost:8080`，控制台将输出：

```
║  ==> action handler: {"name":"访问首页","type":0,"action":0,"level":3,"attributes":{},"timestamp":1553918114710}
```