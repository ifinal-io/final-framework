# final-framework
final-framework

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



## 代码规范

### 日志

使用`slf4j`日志框架进行日志输出，可使用`@Slf4j`注解或静态变量格式，日志变量名为`logger`。

* 静态变量

```java
    private static final Logger logger = LoggerFactory.getLogger(XXX.class);
```

* @Slf4j

在`classpath`下添加`lombok.config`

```properties
lombok.log.fieldName = logger
```
