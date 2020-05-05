---
post: post
title: GlobalExceptionHandler
subtitle: 全局异常处理器
description: 全局异常处理机制。
layout: post
categories: [spring]
tags: [web,exception]
menus:
    - web
    - exception
    - global-exception-handler
author: likly
date: 2019-03-24 17:17:38 +800
version: 1.0
---

# GlobalExceptionHandler

[GlobalExceptionHandler](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/exception/GlobalExceptionHandler.java)
全局异常处理器简化了系统的异常处理，提高系统中异常的可读性。

**定义如下**

```java
public interface GlobalExceptionHandler<T> {

    /**
     * 注册异常处理器
     *
     * @param handler 异常处理器
     */
    void registerExceptionHandler(@NonNull ExceptionHandler<T> handler);

    /**
     * 设置未捕获的异常处理器
     *
     * @param handler 未捕获的异常处理器
     */
    void setUnCatchExceptionHandler(@NonNull ExceptionHandler<T> handler);

    @Nullable
    T handle(@NonNull Throwable throwable) throws Throwable;
}
```