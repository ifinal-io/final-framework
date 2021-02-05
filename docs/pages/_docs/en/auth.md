---
formatter: "@formatter:off"
layout: post 
title: auth 
subtitle: auth 
description: auth 
tags: []
date: 2020-11-24 20:03:17 +800 
version: 1.0
formatter: "@formatter:on"
---

# Auth

## What

基于`Token`的`Auth`认证架构，其中，`Token`用于解释登录用户信息，`Auth`用于权限校验。

![TokenAuth认证流程](http://assets.processon.com/chart_image/5fbcf4970791294615614d02.png)

## Quick Start

### Define TokenService

定义一个`TokenService`的实现类，解析并返回当前登录的用户信息，如果未登录，则返回`null`。

```java

@FunctionalInterface
public interface TokenService {

    /**
     * return the {@link IUser} which parsed from the {@link HttpServletRequest}.
     *
     * @param request  http request
     * @param response http response
     * @return the {@link IUser}
     */
    @Nullable
    IUser token(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response);

}
```

### Define AuthService

定义`AuthService`用于校验当前`Handler(一般是Controller)`是否有相应的权限。

```java

@FunctionalInterface
public interface AuthService<A extends Annotation> {

    /**
     * @param user     the {@link IUser} login, maybe null.
     * @param auth     the {@link Annotation} link {@link Auth}
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param handler  the handler
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    default void auth(@Nullable IUser<?> user, @NonNull A auth, @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull Object handler) {
        auth(user, auth, handler);
    }

    /**
     * @param user
     * @param auth
     * @throws ForbiddenException
     */
    void auth(@Nullable IUser<?> user, @NonNull A auth, @NonNull Object handler);

}
```

### Declare Auth Annotation

在需要`Auth`的`Controller`上声明相应的`@Annotaion`。

```java

@RestController
public class HelloHotswapApiController {

    @Auth //仅需要登录
    @GetMapping("/api/hotswap/hello")
    public String hello() {
        return "hello hotswap!";
    }

}
```

### Request The Url

```json
{
  "status": 403,
  "description": "Forbidden",
  "code": "403",
  "message": "未登录！",
  "data": null,
  "pagination": null,
  "trace": "79d8eea6-0bea-4b0b-bc59-5aeaac53c7d6",
  "timestamp": 1606221030969,
  "duration": "PT0.031S",
  "address": "0:0:0:0:0:0:0:1",
  "ip": "0:0:0:0:0:0:0:1:8080",
  "locale": "zh_CN",
  "timeZone": "Asia/Shanghai",
  "operator": null,
  "view": null,
  "exception": "ForbiddenException",
  "success": false
}
```
