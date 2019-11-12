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


## Definition

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

## Usage

### 

1. Create Your Application Listener

    ```java
    public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    
        private static final Logger logger = LoggerFactory.getLogger(ApplicationReadyEventListener.class);
    
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            logger.info("Application Ready!!!");
        }
    }
    ```

2. Create `spring.factories` file in path of `classpath:META-INF`

    ```properties
    # ApplicationListener
    org.springframework.context.ApplicationListener=\
                org.finalframework.spring.context.listener.ApplicationReadyEventListener
    ```
   
   > 该文件可由编译时注解[@SpringApplicationListener](#SpringApplicationListener)自动生成。

3. Run and see terminal

## How to Load

```java
public class SpringApplication{

    public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
	}
    

	private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
		return getSpringFactoriesInstances(type, new Class<?>[] {});
	}

	private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
		ClassLoader classLoader = getClassLoader();
		// Use names and ensure unique to protect against duplicates
		Set<String> names = new LinkedHashSet<>(
				SpringFactoriesLoader.loadFactoryNames(type, classLoader));
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes,
				classLoader, args, names);
		AnnotationAwareOrderComparator.sort(instances);
		return instances;
	}
    

}
```


## SpringApplicationListener

[SpringApplicationListener](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringApplicationListener.java)
注解在编译时将**直接**或**间隔**标记了该`Annotation`的元素，写到`META-INF/spring.factories`文件中的`org.springframework.context.ApplicationListener`下，
`Spring Boot`在启动时会自动加载这些`ApplicationListener`。

## See

* [SpringFactory](../../../_framework/coding/spring/factory.md): Spring 的`SPI`机制。