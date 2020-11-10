# final-frameworks
Final-frameworks

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

## 核心工程

### final-framework

`final-framework`用于封装常用的技术框架。

|     artifactId     |                      描述                       |
| :----------------: | :---------------------------------------------: |
|    `final-core`    |                                                 |
| `final-annotation` |   定义基本的`Annotation`，为框架解析提供基础    |
|  `final-context`   |                                                 |
|    `final-json`    |       定义统一的Json序列化与反序列化接口        |
|   `final-redis`    |                                                 |
|   `final-cache`    |              基于`AOP`的的缓存切面              |
|  `final-mybatis`   |    定义通用的`Mapper`，提供强大的`CRUD`功能     |
|    `final-web`     | 封装`springMvc`，提供增强的拦截器、异常处理机制 |
|    `final-aop`     |                                                 |
|    `final-data`    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |
|                    |                                                 |



### final-auto

`final-auto`基于`APT（注解处理器）`技术，自动生成常用的配置文件或模板代码，提高开发效率。

|       artifactId        | 描述 |
| :---------------------: | :--: |
| `final-auto-annotation` |      |
|  `final-auto-service`   |      |
|   `final-auto-spring`   |      |
|   `final-auto-coding`   |      |
|    `final-auto-data`    |      |
|   `final-auto-query`    |      |
|  `final-auto-mybatis`   |      |
|                         |      |
|                         |      |




### final-boot

`final-boot`基于`final-framework`，提供快速开始的`starters`。

| artifactId | 描述 |
| :--------: | :--: |
|            |      |
|            |      |
|            |      |
|            |      |
|            |      |
|            |      |
|            |      |
|            |      |
|            |      |



## 代码规范

* [日志规范](docs/code-rules/logger.md)
