---
layout: page
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

* [ApplicationListener](../../../_spring/context/listener/README.md)