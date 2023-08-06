---
formatter: "@formatter:off"
title: 应用监听器
subtitle: application-listener
summary: 监听Spring应用事件
tags: [] 
date: 2021-01-10 23:50:48 +800 
version: 1.0
formatter: "@formatter:on"
---

# 应用监听器

## 概述

在[Spring 应用启动流程](../../boot/spring-application.md)一文中，了解到Spring应用在启动时，可通过高级启动模式中的`addListeners`
或扩展文件`spring.factories`
来注册应用监听器（`ApplicationListener`）。

那么，什么是应用监听器呢？

## 监听器

**应用监听器（`ApplicationListener`） 是 Spring 提供的 `SPI` 扩展点之一，用于监听容器内发生的事件（`ApplicationEvent`
），定义了一个处理应用事件的回调方法`onApplicationEvent(Event)`。**

基定义如下：

```java
package org.springframework.context;

@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);

}
```

## 自定义监听器

应用使用者可以自定义实现应用监听器，监听应用内的事件，处理自己的业务。

如监听应用启动完成事件`ApplicationReadyEvent`。

### 实现 ApplicationListener

1.

```java
package org.ifinalframework.context.listener;

@Slf4j
@SpringApplicationListener
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("Application Ready!!!");
    }

}
```

2. 注册

* `ConfigurableApplicationContext.addApplicationListener`

```java
package org.ifinalframework.example;

@SpringBootApplication
public class FinalApplication {

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(FinalApplication.class);
        application.addListeners(new ApplicationReadyEventListener());
        application.run(args);
    }

}
```

* `spring.factories`

```properties
# org.springframework.context.ApplicationListener
org.springframework.context.ApplicationListener=\
org.ifinalframework.context.listener.ApplicationReadyEventListener
```

> `spring.factories`是Spring提供的SPI配置文件，开发者可以在此配置自定义的扩展，以扩展Spring的功能。
>
> `ApplicationListener`由Spring通过`getSpringFactoriesInstances`方法加载。

### 使用 @EventListener

```java
package org.ifinalframework.example;

@Slf4j
@SpringBootApplication
public class FinalApplication {

    @EventListener
    public void onReady(ApplicationReadyEvent readyEvent) {
        logger.info("onReady from @EventListener");
    }

}
```

### 运行

```shell
--2021-01-15 15:19:21.157 - INFO 6522 --- [           main] o.i.f.example.FinalApplication           : [                                      ] : onReady from @EventListener
--2021-01-15 15:19:21.159 - INFO 6522 --- [           main] o.i.f.c.l.ApplicationReadyEventListener  : [                                      ] : Application Ready!!!
--2021-01-15 15:19:21.159 - INFO 6522 --- [           main] o.i.f.example.FinalApplication           : [                                      ] : onReady from ApplicationListener
```

## 应用事件

通过IDE工具，可以发现`ApplicationEvent`有一个`SpringApplicationEvent`的抽象类，该类定义了Spring应用事件的基本组成。

### Spring 应用事件

Spring 内置了一系列的 Spring 应用事件（`SpringApplicationEvent`），以供开发者监听并处理。

## Build-In Application Listener

* LoggingApplicationListener