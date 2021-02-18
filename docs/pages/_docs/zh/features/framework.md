---
formatter: "@formatter:off"
title: FinalFramework 
subtitle: framework 
summary: framework 
tags: [] 
date: 2021-02-18 13:57:27 +800 
version: 1.0
formatter: "@formatter:on"
---

# Framework

## What

**`FinalFramework`是内置的Spring扩展组件，用于声明加载`final`组件的路径及默认的资源。**

## Feature

### @ComponentScan

声明`@ComponentScan`注解，指定`FinalFramework`组件的包路径，使用默认的类所在包路径，即`org.ifinal.finalframework`来扫描所有的`final`组件。

### @ImportResource

声明`@ImportResource`注解并指定以下资源用于加载特定的组件。

* `classpath:spring-config-*.xml`: 类路径下所有的以`spring-config-`开头的`xml`文件。
* `classpath*:config/spring-config-*.xml`: `config`路径下所有的以`spring-config-`开头的`xml`文件。
* `classpath*:spring/spring-config-*.xml`: `spring`路径下所有的以`spring-config-`开头的`xml`文件。

