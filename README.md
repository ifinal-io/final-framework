# final-frameworks

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)


## 概述

`final-frameworks`致力于提供简单、灵活且功能强大的`java`开发脚手架。

## 核心内容

* [增强的JSON](docs/json.md)：对日期、枚举等数据类型进行序列化增强，提高`JSON`可读性。
* [强大的WEB](docs/web.md)
    * [统一的结果集](docs/web.md#统一的结果集): 对`@RestController`的结果进行统一拦截封装
    * [全局异常处理](docs/web.md#全局异常处理)：对系统中抛出的`Exception`进行全局的拦截处理，转化为统一的结果集。
* [极简的监控](docs/monitor.md)  
    * 简单的操作日志




## 集成技术

|      技术      |           说明           |                             官网                             |
| :------------: | :----------------------: | :----------------------------------------------------------: |
|     Lombok     |     简化对象封闭工具     | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
|  Spring Boot   |   Spring容器及MVC框架    | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
|    Mybatis     |         ORM框架          | [https://mybatis.org/mybatis-3/zh/index.html](https://mybatis.org/mybatis-3/zh/index.html) |
|   PageHelper   |     Mybatis分页插件      | [https://github.com/pagehelper/Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) |
|     Redis      |        分布式缓存        |            [https://redis.io/](https://redis.io/)            |
|     Dubbo      |      分布式RPC调用       | [http://dubbo.apache.org/zh-cn/](http://dubbo.apache.org/zh-cn/) |
| ShardingSphere |       分库分表组件       | [https://shardingsphere.apache.org/document/current/cn/overview/](https://shardingsphere.apache.org/document/current/cn/overview/) |
|   Zookeeper    | 分布式注册中心、配置中心 |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |

## 工程概述

* [`final-framework`](final-framework/README.md): 对常用技术进行封装及增强，如`spring`、`myabtis`、`json`等。
* [`final-auto`](final-auto/README.md)：基于`APT`生动化生成配置文件及模板代码，提升开发效率。
* [`final-boot`](final-boot/README.md): 基于`Spring Boot`，提供开箱即用的`starters`。

## 开发规范

* [日志规范](docs/code-rules/logger.md)

## 致谢

* 感谢<a href="https://www.jetbrains.com/"><img src="https://www.jetbrains.com/apple-touch-icon.png" width="64" height="64">jetbrains</a>提供的免费授权。
