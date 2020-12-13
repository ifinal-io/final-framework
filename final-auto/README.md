---
layout: post title: README subtitle: README description: README tags: []
menus:

- README date: 2020-11-11 14:18:13 +800 version: 1.0

---

# final auto

`final-auto`基于`APT（注解处理器）`技术，自动生成常用的配置文件或模板代码，提高开发效率。

|       artifactId        |                             描述                             |
| :---------------------: | :----------------------------------------------------------: |
| `final-auto-annotation` |                                                              |
|  `final-auto-core`   | 自动生成`META-INF/services`文件，支持JDK原生`SPI`及`Dubbo`扩展`SPI` |
|   `final-auto-spring`   | 自动生成`META-INF/spring.factories`文件，支持对`SpringFactory`进行扩展 |
|   `final-auto-coding`   |        使用`Velocity`模板引擎，提供模板代码生成的基础        |
|    `final-auto-data`    |  |
| `final-auto-processor` | 解析所有实现了`IEntity`接口的类，生成`META-INF/service/**.IEntity`文件，供其他组件使用 |
|   `final-auto-query`    | 1. 解析`META-INF/service/**.IEntity`文件，自动生成`QEntity`源文件 |
|  `final-auto-mybatis`   | 1. 解析`META-INF/service/**.IEntity`文件，自动生成`EntityMapper`源文件 |
|  `final-auto-service`  | 解析`META-INF/service/**.IEntity`文件，自动生成`EntityService`及`EntityServiceImpl`源文件 |


