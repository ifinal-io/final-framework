---
layout: post
title: ApplicationListener
subtitle: Spring Observer Design Pattern
description: fd
categories: []
tags: []
permalink: /spring/context/application-listener
menus:
    - context
    - application-listener
    - index
author: likly
date: 2019-11-11 20:48:10 +800
version: 1.0
---

# Listener

## What

[ApplicationListener](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/context/ApplicationListener.java)
是`Spring`提供的`Observer design pattern(观察者模式)`接口，可以观察到`Spring`的触发的各种[ApplicationEvent(事件)](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/context/ApplicationEvent.java)`。

## Listener

### Definition

```java
package org.springframework.context;

import java.util.EventListener;

/**
 * Interface to be implemented by application event listeners.
 * Based on the standard {@code java.util.EventListener} interface
 * for the Observer design pattern.
 *
 * <p>As of Spring 3.0, an ApplicationListener can generically declare the event type
 * that it is interested in. When registered with a Spring ApplicationContext, events
 * will be filtered accordingly, with the listener getting invoked for matching event
 * objects only.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @param <E> the specific ApplicationEvent subclass to listen to
 * @see org.springframework.context.event.ApplicationEventMulticaster
 */
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

	/**
	 * Handle an application event.
	 * @param event the event to respond to
	 */
	void onApplicationEvent(E event);

}

```

### Register

* SpringApplication

```java
org.springframework.boot.SpringApplication.addListeners
```

* spring.factories

```properties
# ApplicationListener
org.springframework.context.ApplicationListener=\
            org.finalframework.spring.context.listener.ApplicationContextInitializedEventListener,\
            org.finalframework.spring.context.listener.ApplicationEnvironmentPreparedEventListener,\
            org.finalframework.spring.context.listener.ApplicationFailedEventListener,\
            org.finalframework.spring.context.listener.ApplicationStartingEventListener,\
            org.finalframework.spring.context.listener.ApplicationReadyEventListener,\
            org.finalframework.spring.context.listener.ApplicationStartedEventListener,\
            org.finalframework.spring.context.listener.ApplicationPreparedEventListener
```

* [SpringFactory](../../../_framework/coding/spring/factory.md)