---
post: post
title: RequestJsonParam
subtitle: 表单形式的Json格式
description: “前端”同学不直接传Json到后端，哎，你们高兴就好！
categories: []
layout: page
menus:
    - web
    - resolver
    - request-json-param
tags: []
author: likly
date: 2019-03-16 19:15:48 +800
version: 1.0
---

# RequestJsonParam

## What

[RequestJsonParam](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/resolver/annotation/RequestJsonParam.java)，
一个自定义的参数解析器，能够解析形如 `name=json` 的参数格式。

## Usage

如同使用`RequestParam`一样，在需要处理形如 `name=json` 的参数时，在参数上声明 `RequestJsonParam` 注解即可。

```java
@RestController
@RequestMapping("/params")
public class RequestJsonParamController{
    
    @RequestMapping("/test1")
    public Param test1(@ReqestJsonParam Param param){
        return param;
    }
    
    @RequestMapping("/test2")
    public Param test1(@ReqestJsonParam("param") Param request){
        return request;
    }
}
```
