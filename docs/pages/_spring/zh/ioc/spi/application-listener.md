---
formatter: off
layout: post
title: 应用监听器
subtitle: application-listener 
description: application-listener 
tags: [] 
date: 2021-01-10 23:50:48 +800 
version: 1.0
formatter: on
---

# 应用监听器

## 概述

**`ApplicationListener` 是 Spring 提供的 `SPI` 扩展点之一，用于监听容器内发生的事件（`ApplicationEvent`）。**

## 定义

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

## Spring 应用事件

Spring 内置了一系列的 Spring 应用事件（`SpringApplicationEvent`），以供开发者监听并处理。

* 内置 Spring 应用事件

| SpringApplicationEvent              |      |      |
| ----------------------------------- | ---- | ---- |
| ApplicationStartingEvent            | 1    |      |
| ApplicationEnvironmentPreparedEvent | 2    |      |
| ApplicationContextInitializedEvent  | 3    |      |
| ApplicationPreparedEvent            | 4    |      |
| ApplicationStartedEvent             | 5    |      |
| ApplicationReadyEvent               | 6    |      |
| ApplicationFailedEvent              | -1   |      |

## 自定义监听器

### 实现

```java
package org.ifinal.finalframework.context.listener;

@Slf4j
@SpringApplicationListener
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("Application Ready!!!");
    }

}
```

### 注册

* `ConfigurableApplicationContext.addApplicationListener`

```java
package org.ifinal.finalframework.example;

@SpringBootApplication
public class FinalApplication {

    public static void main(final String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FinalApplication.class);
        context.addApplicationListener(new ApplicationReadyEventListener());
    }

}
```

* `spring.factories`

```properties
# org.springframework.context.ApplicationListener
org.springframework.context.ApplicationListener=\
org.ifinal.finalframework.context.listener.ApplicationReadyEventListener
```

### 运行

```shell
--2021-01-10 23:58:24.204 - INFO 43543 --- [           main] o.i.f.c.l.ApplicationReadyEventListener  : [                                      ] : Application Ready!!!
```