---
post: post
title: HandlerInterceptor
subtitle: 处理器拦截器
layout: post
categories: [spring]
tags: [web,interceptor]
menus:
    - web
    - interceptor
    - handler-interceptor
author: likly
date: 2019-03-24 19:48:48 +800
version: 1.0
---

# HandlerInterceptor

`HandlerIntercetor`处理器拦截器可以说是`Spring`中一个重要的组件，它可以在处理器`HandlerMethod`执行之前或之后进行特殊的处理。
如登录鉴权等。

处理器拦截器虽说很重要，但是使用起来（配置）却有点繁琐，尤其是在某些拦截器在开发时并不想启用时。因此，
[@HandlerInterceptor](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/interceptor/annotation/HandlerInterceptor.java)
注解应运而生，
它简化了`HandlerIntercetor`的声明与配置，再配合上`@Profile`，完美实现根据环境而加载。

## Defaults

* [TraceHandlerInterceptor](trace-handler-interceptor.md)：为每一个请求设置一个追踪`trace`，方便过滤日志。
