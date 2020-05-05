---
layout: post
title: Exception
subtitle: Exception
description: 异常描述性接口和全局的异常处理机制。
categories: []
tags: []
menus:
    - data
    - exception
author: likly
date: 2019-11-13 09:43:24 +800
version: 1.0
---

# Exception

`Exeption`是业务中不可避免的存在，它不仅仅在程序`Bug`时存在，在实际的业务场景中，`Exception`有时能让大大提高程序的可读性。

## IException

[IException](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/IException.java)
为定义的`Exception`描述性接口，提供了`code`、`message`和`toast`属性，可由[Handler](#handler)处理。

```java
package org.finalframework.data.exception;

import lombok.NonNull;
import org.finalframework.data.exception.handler.ExceptionHandler;
import org.finalframework.data.result.Result;
import org.springframework.lang.Nullable;

/**
 * 统一异常处理接口，实现该接口的异常，将会被{@literal Spring}的异常处理机制拦截，
 * 并将异常所声明的错误码{@link #getCode()}和错误消息{@link #getMessage()}封装到{@link Result}中。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @see Result
 * @see ExceptionHandler
 * @since 1.0
 */
public interface IException {

    @NonNull
    Integer getCode();

    @NonNull
    String getMessage();

    @Nullable
    default String getToast() {
        return null;
    }
}

```

## Handler

### ExceptionHandler

```java
package org.finalframework.data.exception.handler;

import org.springframework.lang.NonNull;

/**
 * 异常处理器
 *
 * @param <R> 返回的结果
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface ExceptionHandler<E, R> {

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
    R handle(@NonNull E throwable);
}

```


### GlobalExceptionHandler

[GlobalExceptionHandler](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/handler/GlobalExceptionHandler.java)
全局异常处理器接口，提供了注册`ExceptionHandler(异常处理器)`的入口`registerExceptionHandler(ExceptionHandler)`和`setUnCatchExceptionHandler(ExceptionHandler)`

```java
package org.finalframework.data.exception.handler;

import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 全局异常处理器，将系统中抛出的业务异常或非业务异常转化为可读的结果返回给调用者。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 16:00:13
 * @see AbsGlobalExceptionHandler
 * @see ResultGlobalResultExceptionHandler
 * @since 1.0
 */
public interface GlobalExceptionHandler<T> {

    /**
     * 注册异常处理器
     *
     * @param handler 异常处理器
     */
    void registerExceptionHandler(@NonNull ExceptionHandler<Throwable, T> handler);

    /**
     * 设置未捕获的异常处理器
     *
     * @param handler 未捕获的异常处理器
     */
    void setUnCatchExceptionHandler(@NonNull ExceptionHandler<Throwable, T> handler);

    /**
     * 将异常 {@link Throwable} 转化为可读的结果 {@link T}
     *
     * @param throwable 异常
     */
    @Nullable
    T handle(@NonNull Throwable throwable);
}

```

## See Also

* [IExceptionResultExceptionHandler](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/result/IExceptionResultExceptionHandler.java)
* [IExceptionResultExceptionHandler](/final-data/final-data-context/src/main/java/org/finalframework/data/exception/result/ViolationResultExceptionHandler.java)
* [JsonExceptionHandler](/final-spring/final-spring-web/src/main/java/org/finalframework/spring/web/exception/result/JsonExceptionHandler.java)
