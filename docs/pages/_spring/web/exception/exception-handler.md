---
post: post
title: ExceptionHandler
subtitle: 异常处理器
description: 简化项目中的异常处理，包含程序异常和业务异常。
layout: post
categories: [spring]
tags: [web,exception]
menus:
    - web
    - exception
    - exception-handler
author: likly
date: 2019-03-24 17:23:56 +800
version: 1.0
---

# ExceptionHandler

## What

[ExceptionHandler](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/ExceptionHandler.java)
异常处理器简化了项目中的异常处理。

**定义**：

```java
public interface ExceptionHandler<R> {

    /**
     * 返回该异常处理器是否支持该异常，如果支持则返回 {@code true}，否则返回 {@code false}。
     *
     * @param throwable 业务方法抛出的异常
     * @return 是否可以处理该异常
     */
    boolean supports(@NonNull Throwable throwable);

    /**
     * 将异常转化成可视化的结果
     *
     * @param throwable 业务方法抛出的异常
     * @return 异常转化后的结果
     */
    @NonNull
    R handle(@NonNull Throwable throwable);
}

```

## Usage

自定义异常处理器只需要实现[ExceptionHandler](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/ExceptionHandler.java)
接口，并注册到全局异常处理器[GlobalExceptionHandler](global-exception-handler.md)中，即可实现异常处理器的自定义。

```java
public class UnCatchExceptionHandler implements ExceptionHandler{
    
    @Resource
    private GlobalExceptionHandler globalExceptionHandler;
    
    @PostConstruct
    public void init(){
        globalExceptionHandler.registerExceptionHandler(this);
    }
    
    @Override
    public boolean supports(@NonNull Throwable t) {
        return true;
    }
    
    @NonNull
    @Override
    public Result handle(Throwable e) {
        return R.failure(500, e.getMessage());
    }
}
```

为进一步简化异常处理器的开发，定义了`@RestExceptionHandler`注解，只需要在自定义的异常处理器上声明该注解，即可实现异常处理的注册。

即，上面的异常处理器可简化为：

```java
@RestExceptionHandler
public class DefaultExceptionHandler implements ExceptionHandler{
    
    @Override
    public boolean supports(@NonNull Throwable t) {
        return true;
    }
    
    @NonNull
    @Override
    public Result handle(Throwable e) {
        return R.failure(500, e.getMessage());
    }
}
```