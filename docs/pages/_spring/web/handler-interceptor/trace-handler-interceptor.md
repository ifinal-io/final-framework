---
post: post
title: TraceHandlerInterceptor
subtitle: Trace处理器拦截器
layout: page
categories: []
tags: []
author: likly
date: 2019-03-24 22:18:40 +800
version: 1.0
---

# TraceHandlerInterceptor

## What

[TraceHandlerInterceptor](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/interceptor/TraceHandlerInterceptor.java)
用于在一次请求中获取或生成一个请求`trace`并设置到日志上下文`MDC`中。