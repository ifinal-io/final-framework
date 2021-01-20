---
formatter: off
title: 应用事件发布者
subtitle: application-event-publisher 
description: application-event-publisher 
tags: [] 
date: 2021-01-11 22:27:25 +800 
version: 1.0
formatter: on
---

# 应用事件发布者

## 简介

**应用事件发布者**（`ApplicationEventPublisher`）是 Spring 内置的观察者模式中的生产者，用于发布**应用事件**（`ApplicationEvent`）。

## 定义

```java
package org.springframework.context;

@FunctionalInterface
public interface ApplicationEventPublisher {

    default void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    void publishEvent(Object event);

}
```

## 实现

```java
package org.springframework.context.support;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
    implements ConfigurableApplicationContext {

    @Override
    public void publishEvent(Object event) {
        publishEvent(event, null);
    }

    protected void publishEvent(Object event, @Nullable ResolvableType eventType) {
        Assert.notNull(event, "Event must not be null");

        // Decorate event as an ApplicationEvent if necessary
        ApplicationEvent applicationEvent;
        if (event instanceof ApplicationEvent) {
            applicationEvent = (ApplicationEvent) event;
        } else {
            applicationEvent = new PayloadApplicationEvent<>(this, event);
            if (eventType == null) {
                eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();
            }
        }

        // Multicast right now if possible - or lazily once the multicaster is initialized
        if (this.earlyApplicationEvents != null) {
            this.earlyApplicationEvents.add(applicationEvent);
        } else {
            getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
        }

        // Publish event via parent context as well...
        if (this.parent != null) {
            if (this.parent instanceof AbstractApplicationContext) {
                ((AbstractApplicationContext) this.parent).publishEvent(event, eventType);
            } else {
                this.parent.publishEvent(event);
            }
        }
    }

}
```



