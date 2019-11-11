---
layout: post
title: SpringFactory
subtitle: Spring SPI 机制
categories: [coding]
tags: [spring,factory]
menus:
    - coding
    - spring
    - factory
author: likly
date: 2019-10-29 10:19:57 +800
version: 1.0
---

# SpringFactory

[@SpringFactory](/final-coding/final-coding-spring/src/main/java/org/finalframework/coding/spring/factory/annotation/SpringFactory.java)
实现将直接或间接标记了`@SpringFactory`注解的类自动的写入`META-INF/spring.factories`文件中，以供`Spring`容器解析加载。

1. `@SpringFactory`可作用于`ElementType.ANNOTATION_TYPE`，因此可自定义实现`SpringFactory`的扩展功能。
2. `@SpringFactory`也可作用于`ElementType.PACKAGE`，会将该包下所有含有指定`Annotation`的类全部获写入到指定文件中。

## Expands

### Configuration

[SpringConfiguration](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringConfiguration.java)


### ApplicationListener

`ApplicationListener`是`Spring`提供的应用监听接口，可以监听`Spring Application`产生的各种`Event`。

```java
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

	/**
	 * Handle an application event.
	 * @param event the event to respond to
	 */
	void onApplicationEvent(E event);

}
```

 [@SpringApplicationListener](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringApplicationListener.java)
注解则可以将工程中的`ApplicationListener`实现类写入到文件`META-INF/spring.factories`中的`org.springframework.context.ApplicationListener`配置下，在`Spring`启动时，自动加载注册。

### ResponseBodyAdvice

### WebMvcConfigurer

1. [SpringResponseBodyAdvice](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringResponseBodyAdvice.java)
1. [SpringWebMvcConfigurer](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringWebMvcConfigurer.java)
1. [SpringComponent](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringComponent.java)