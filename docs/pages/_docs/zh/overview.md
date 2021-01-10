---
formatter: off
layout: post
title: 概览 
subtitle: overview 
description: overview 
tags: [] 
date: 2021-01-08 10:34:33 +800 
version: 1.0
formatter: on
---

# 概览

## 概述

[![Maven Central](https://img.shields.io/maven-central/v/org.ifinal.finalframework.frameworks/final-frameworks?label=maven&color=success)](https://mvnrepository.com/search?q=org.ifinal.finalframework)
[![GitHub Repo stars](https://img.shields.io/github/stars/likly/final-frameworks)](https://github.com/likly/final-frameworks)

**`Fianl` 是一个*简单的*、*易用的*、*可扩展的* `Java` 开发脚手架。**

`Final` 基于`spring` 开发，集成了大量流行的开源项目，以提供更为易用的开发方式。

`Final` 也是`Java`开发的最佳实践之一，将工作中用到的Java技术和遇到的问题，通过更深入的研究与学习后整合到本项目中，然后再引入到工作项目中，从而使本项目经过更多的线上验证。

## 模块

|                          模块                          |       描述        |                           版本                            |
| :----------------------------------------------------------: | :----------------------: | :----------------------------------------------------------: |
|       final-annotation      |     定义核心的注释（`Annotation`）和接口（`Interface`）     | |
|       final-framework      |    Final 核心模块       | |
|       final-data      |    数据访问，如`mybatis`、`redis`、`shardingshpere`       | |
|       final-cloud      |    云应用       | |
|       final-auto      |   模板代码生成工具     | |
|       final-boot      |    快速集成      | |

## Dependencies

### Core

|                          Dependency                          |       Description        |                           Version                            |
| :----------------------------------------------------------: | :----------------------: | :----------------------------------------------------------: |
|    [Spring Boot](https://spring.io/projects/spring-boot)     |   Spring容器及MVC框架    | [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-parent?label=2.3.3-RELEASE)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot) |
|    [Mybatis](https://mybatis.org/mybatis-3/zh/index.html)    |         ORM框架          | ![Maven Central](https://img.shields.io/maven-central/v/org.mybatis/mybatis?label=3.5.6) |
|           [Dubbo](http://dubbo.apache.org/zh-cn/)            |      分布式RPC调用       | ![Maven Central](https://img.shields.io/maven-central/v/org.apache.dubbo/dubbo-spring-boot-starter?label=2.7.8) |
| [ShardingSphere](https://shardingsphere.apache.org/document/current/cn/overview/) |       分库分表组件       | ![Maven Central](https://img.shields.io/maven-central/v/org.apache.shardingsphere/shardingsphere-jdbc-core?label=5.0.0-alpha) |
|                          Zookeeper                           | 分布式注册中心、配置中心 |                                                              |
|                                                              |                          |                                                              |

### Plugins

|                          Dependency                          |       Description        |                           Version                            |
| :----------------------------------------------------------: | :----------------------: | :----------------------------------------------------------: |
|       [Lombok](https://github.com/rzwitserloot/lombok)       |     简化对象封装工具     | ![Maven Central](https://img.shields.io/maven-central/v/org.projectlombok/lombok?label=1.8.16) |
| [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) |     Mybatis分页插件      | ![Maven Central](https://img.shields.io/maven-central/v/com.github.pagehelper/pagehelper?label=5.2.0) |
|        [javapoet](https://github.com/square/javapoet)        |      Java源代码生成      | ![Maven Central](https://img.shields.io/maven-central/v/com.squareup/javapoet?label=1.13.0) |
|                        Velocity-Core                         |                          | ![Maven Central](https://img.shields.io/maven-central/v/org.apache.velocity/velocity-engine-core?label=2.1) |
|                        Velocity-Tools                        |                          | ![Maven Central](https://img.shields.io/maven-central/v/org.apache.velocity.tools/velocity-tools-generic?label=3.0) |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |

### Test

|                          Dependency                          |       Description        |                           Version                            |
| :----------------------------------------------------------: | :----------------------: | :----------------------------------------------------------: |
|                          H2Database                          |        内存数据库        | ![Maven Central](https://img.shields.io/maven-central/v/com.h2database/h2?label=1.4.200) |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
|                                                              |                          |                                                              |
