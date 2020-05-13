---
layout: post
title: SpringApplicationListener
subtitle: Spring Factory Application Listener 扩展
description: 自动生成`spring.factories`文件，提高开发效率
categories: [spring]
tags: [annotation,factory]
menus:
    - annotation
    - factory
    - application-listener
author: likly
date: 2019-11-12 11:16:50 +800
version: 1.0
---

# SpringApplicationListener

## What

[@SpringApplicationListener](/final-spring/final-spring-annotation/src/main/java/org/finalframework/spring/annotation/factory/SpringApplicationListener.java)
注解是实现了`Spring`的[]()的`SPI`扩展机制。

## Definition

```java
package org.finalframework.spring.annotation.factory;

import org.finalframework.spring.annotation.factory.SpringFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the target type is a spring application listener
 *
 * @author likly
 * @version 1.0
 * @date 2018-12-25 22:15:37
 * @see SpringApplication#getListeners()
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(ApplicationListener.class)
public @interface SpringApplicationListener {
}

```