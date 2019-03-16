---
post: post
title: RequestJsonParam
subtitle: request-json-param
description: “前端”同学不直接传Json到后端，哎，你们高兴就好！
categories: []
layout: post
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

## Why

我相信每一个接触过`API`的同学都知道，数据在传输过程中的数据格式一般有以下几种：

* **表单**，通常为请求参数，一般为`GET`方法。
* **Json**: 请求体或响应体，请求时一般为`POST`方法。
* **Xml**：请求体或响应体，以以前的API中使用较多，现在新开发的基本上都是`Json`。

我也相信，总有那么些前端同学，由于种种原因，创造了一种另类的**表单**格式：

```
param={"name":"value"}
```

我也相信，下面的代码你`CV`了无数次：

```java
String paramJson = request.getParameter("param");
Param param = Json.parse(paramJson,Param.class);
```

我更相信，会有那么一些同学会想，有没有可能像`@RequestParam`、`@PathVariable`和`@RequestBody`那样解决这个问题呢？

## This's our Want
通过查看`@RequestParam`和`@RequestBody`的源码，如法炮制了`@RequestJsonParam`:

```java
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonParam {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
```

注解定义好了，是不是就可以使用了呢？

## How to use

就像使用`@RequestParam`那样，在需要处理的参数上声明`@RequestJsonParam`注解，就像这样：

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

这样是不是就可以了呢？我们尝试请求两个方法，发现并没有达到我们想要的目的。

## How to let it work

`RequestJsonParam`注解并没有像想像的好样，将`param={}`的参数映射到`Param`中去，这是为什么呢？
通过查阅前辈们踩过的坑，了解到`Spring`提供了参数解析器接口`HandlerMethodArgumentResolver`，可以达到我们想要的效果，定义如下：

```java
public interface HandlerMethodArgumentResolver {

	/**
	 * Whether the given {@linkplain MethodParameter method parameter} is
	 * supported by this resolver.
	 * @param parameter the method parameter to check
	 * @return {@code true} if this resolver supports the supplied parameter;
	 * {@code false} otherwise
	 */
	boolean supportsParameter(MethodParameter parameter);

	/**
	 * Resolves a method parameter into an argument value from a given request.
	 * A {@link ModelAndViewContainer} provides access to the model for the
	 * request. A {@link WebDataBinderFactory} provides a way to create
	 * a {@link WebDataBinder} instance when needed for data binding and
	 * type conversion purposes.
	 * @param parameter the method parameter to resolve. This parameter must
	 * have previously been passed to {@link #supportsParameter} which must
	 * have returned {@code true}.
	 * @param mavContainer the ModelAndViewContainer for the current request
	 * @param webRequest the current request
	 * @param binderFactory a factory for creating {@link WebDataBinder} instances
	 * @return the resolved argument value, or {@code null} if not resolvable
	 * @throws Exception in case of errors with the preparation of argument values
	 */
	@Nullable
	Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception;

}
```

该接口有两个方法，其中`supportsParameter`方法是判断该参数解析器是否支持需要解析的参数，`resolveArgument`是解析方法，
当且仅当`supportsParameter`的返回值为`true`时，才会执行`resolveArgument`方法。

要实现自定义参数解析器，主要有两步：
1. 声明自定义参数解析器。
2. 注册自定义参数解析器。

既然如此，那就来定义我们的`RequestJsonParamHandlerMethodArgumentResolver`解析器。
首先，解析器应该仅支持参数上有`@ReqestJsonParam`的`MethodParameter`，如下：

```java
public class RequestJsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 仅支持参数上声明了 RequestJsonParam 的参数
        return parameter.hasParameterAnnotation(RequestJsonParam.class);
    }
}    
```

其次，将`@RequestJsonParam`所对应的参数（默认为声明的参数名，也可以通过`name`或`value`指定）从请求中取出，并将其反序列化为参数所声明的类型。

```java
public class RequestJsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 仅支持参数上声明了 RequestJsonParam 的参数
        return parameter.hasParameterAnnotation(RequestJsonParam.class);
    }
    
    @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            RequestJsonParam requestJsonParam = parameter.getParameterAnnotation(RequestJsonParam.class);
            if (Assert.isNull(requestJsonParam)) {
                // it can not enter
                throw new NullPointerException("requestJsonParam annotation is null");
            }
    
            final Type parameterType = parameter.getGenericParameterType();
                final String parameterName = getParameterName(requestJsonParam, parameter);
                String value = webRequest.getParameter(parameterName);
                try {
                    if (Assert.isEmpty(value) && requestJsonParam.required()) {
                        throw new BadRequestException("parameter %s is required", parameterName);
                    }
    
                    if (Assert.isEmpty(value) && !ValueConstants.DEFAULT_NONE.equals(requestJsonParam.defaultValue())) {
                        value = requestJsonParam.defaultValue();
                    }
    
                    if (Assert.isEmpty(value)) return null;
                    logger.debug("==> RequestJsonParam: name={},value={}", parameterName, value);
                    return parseJson(value, parameterType);
                } catch (Throwable e) {
                    logger.error("==> Json解析异常：json={},type={}", value, parameterType, e);
                    throw e;
                }
    
    
        }
    
        private Object parseJson(String json, Type type) {
            return Json.parse(json, type);
        }
    
        /**
         * 获取指定的参数名，如果{@link RequestJsonParam#value()}未指定，则使用{@link MethodParameter#getParameterName()}。
         */
        private String getParameterName(RequestJsonParam requestJsonParam, MethodParameter parameter) {
            return Assert.isEmpty(requestJsonParam.value()) ? parameter.getParameterName() : requestJsonParam.value().trim();
        }
}    
```



