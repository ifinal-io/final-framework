# final-frameworks

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/likly/final-frameworks/CI)
[![GitHub](https://img.shields.io/github/license/likly/final-frameworks)](http://www.apache.org/licenses/LICENSE-2.0.html)
![Maven Central](https://img.shields.io/maven-central/v/org.ifinal.finalframework.frameworks/final-frameworks)
[![GitHub Repo stars](https://img.shields.io/github/stars/likly/final-frameworks)](https://github.com/likly/final-frameworks)
[![GitHub language count](https://img.shields.io/github/languages/count/likly/final-frameworks)](https://github.com/likly/final-frameworks)



## What

`final-frameworks`致力于提供简单、灵活且功能强大的`java`开发脚手架。

## Quick Start

* [Define Entity with Annotation](docs/quick-start/define-entity-with-annotation)：使用`@Annotation`定义实体。
* [Operating CRUD with Mapper](docs/quick-start/operating-crud-with-mapper.md)：使用`Mapper`操作`CRUD`。
* [Start with Service](docs/quick-start/start-with-service.md)：从`Service`开始。

## 核心内容

* [增强的JSON](docs/json.md)：对日期、枚举等数据类型进行序列化增强，提高`JSON`可读性。
* [通用的CURD](docs/crud.md)：定义通用的CURD方法，统一数据的持久化。
* [简明的查询](docs/query.md)：基于注解的声明式查询，使查询简单明了。
* [强大的WEB](docs/web.md)
    * [统一的结果集](docs/web.md#统一的结果集): 对`@RestController`的结果进行统一拦截封装
    * [全局异常处理](docs/web.md#全局异常处理)：对系统中抛出的`Exception`进行全局的拦截处理，转化为统一的结果集。
    * [TokenAuth认证机制](docs/auth.md)：简化的权限认证机制。
* [极简的监控](docs/monitor.md)  
    * [简单的操作日志](docs/monitor.md#简化的操作日志)
* Devops
    * [热更新](docs/hotswap.md)    


## 工程概述

* [`final-framework`](final-framework/README.md): 对常用技术进行封装及增强，如`spring`、`myabtis`、`json`等。
* [`final-auto`](final-auto/README.md)：基于`APT`生动化生成配置文件及模板代码，提升开发效率。
* [`final-boot`](final-boot/README.md): 基于`Spring Boot`，提供开箱即用的`starters`。

## 开发规范

* [日志规范](docs/code-rules/logger.md)


## 集成技术

|                             技术                             |           说明           |
| :----------------------------------------------------------: | :----------------------: |
|       [Lombok](https://github.com/rzwitserloot/lombok)       |     简化对象封闭工具     |
|    [Spring Boot](https://spring.io/projects/spring-boot)     |   Spring容器及MVC框架    |
|    [Mybatis](https://mybatis.org/mybatis-3/zh/index.html)    |         ORM框架          |
| [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) |     Mybatis分页插件      |
|                  [Redis](https://redis.io/)                  |        分布式缓存        |
|           [Dubbo](http://dubbo.apache.org/zh-cn/)            |      分布式RPC调用       |
| [ShardingSphere](https://shardingsphere.apache.org/document/current/cn/overview/) |       分库分表组件       |
|                          Zookeeper                           | 分布式注册中心、配置中心 |
|       [javapoet](https://github.com/square/javapoet)       |      Java源代码生成      |

## Contact

欢迎有技术情怀的同学一起成长！

* <a href="mailto:likly@ilikly.com?subject=Concat from github">MailTO:likly@ilikly.com</a>



## 致谢

* 感谢<a href="https://www.jetbrains.com/"><img src="https://www.jetbrains.com/apple-touch-icon.png" width="64" height="64">jetbrains</a>提供的免费授权。
