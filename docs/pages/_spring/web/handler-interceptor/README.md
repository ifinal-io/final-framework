---
post: post
title: HandlerInterceptor
subtitle: 处理器拦截器
layout: post
categories: [spring]
tags: [web,handler-interceptor]
permalink: /spring/web/handler-interceptor/
menus:
    - web
    - handler-interceptor
    - index
author: likly
date: 2019-03-24 19:48:48 +800
version: 1.0
---

# HandlerInterceptor

## What

`HandlerIntercetor`处理器拦截器可以说是`Spring`中一个重要的组件，它可以在处理器`HandlerMethod`执行之前或之后进行特殊的处理。
如登录鉴权等。

## Definition

要添加自定义的`HandlerIntercetor`拦截器，只需要实现`Spirng`内置的`HandlerInterceptor`或`AsyncHandlerInterceptor`接口，接口定义分别如下：

### HandlerInterceptor

```java
package org.springframework.web.servlet;

public interface HandlerInterceptor {
	
	default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}
```

### AsyncHandlerInterceptor

```java
package org.springframework.web.servlet;

public interface AsyncHandlerInterceptor extends HandlerInterceptor {

	default void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
	}

}
```

## Registration

### WebMvcConfigurer

`Spring`提供了注册`HandlerInterceptor`的接口`WebMvcConfigurer`,使用方式如下:

```java
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorWebMvcConfigurer.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 创建一个 HandlerInterceptor
        HandlerInterceptor interceptor = new TraceHandlerInterceptor();
        // 2. 注册 HandlerInterceptor
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);
        // 3. 添加包含的路径
        interceptorRegistration.addPathPatterns("/");
        // 4. 添加排除的路径（可选）
        interceptorRegistration.excludePathPatterns(annotation.excludes());
        // 5. 设置排序
        interceptorRegistration.order(order.value());
    }

}
```

### SpringHandlerInterceptor

处理器拦截器虽说很重要，但是使用起来（配置）却有点繁琐，尤其是在某些拦截器在开发时并不想启用时。因此，
[@SpringHandlerInterceptor](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringHandlerInterceptor.java)
注解应运而生，
它简化了`HandlerIntercetor`的声明与配置，再配合上`@Profile`，完美实现根据环境而加载。

使用步骤如下：

1. 定义`HandlerInterceptor`
2. 声明`@SpringHandlerInterceptor`注解
3. 配置`includes()`和`excludes()`（可选）
4. 设置`@Order`（可选）
5. 设置`@Profile`（可选）

> 如: [TraceHandlerInterceptor](trace-handler-interceptor.md)

```java
@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringHandlerInterceptor
public class TraceHandlerInterceptor implements AsyncHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);
    
    private static final String TRACE_NAME = "trace";

    public static final String TRACE_ATTRIBUTE = "org.finalframework.handler.trace";

    private final TraceGenerator traceGenerator = new UUIDTraceGenerator();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 header 中是否有自定义的 trace
        String trace = request.getHeader(TRACE_NAME);
        if (trace == null) {
            // 如果 header 中没有 trace，则获取 Request 域中的 trace 属性
            trace = (String) request.getAttribute(TRACE_ATTRIBUTE);
        }
        if (trace == null) {
            // 如果 trace 还为空，则生成一个新的 trace
            trace = traceGenerator.generate();
        }
        request.setAttribute(TRACE_ATTRIBUTE, trace);
        MDC.put(TRACE_NAME, trace);
        logger.info("put trace to MDC context: {}", trace);
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("remove trace from MDC context");
        MDC.remove(TRACE_NAME);
    }
}
```

## See Also

* [TraceHandlerInterceptor](trace-handler-interceptor.md)：为每一个请求设置一个追踪`trace`，方便过滤日志。
