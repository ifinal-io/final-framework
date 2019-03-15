# final-framework

## Modules

* [`final-core`](final-core/README.md)：提供了能用的`interface`，如转换器`Converter`、过滤器`Filter`等。
* [`final-data`](final-data/README.md)：数据对象模型化，为简化数据的CURD做基础。
    * [`final-data-annotation`](final-data/final-data-annotation/README.md)
    * [`final-data-core`](final-data/final-data-core/README.md)
    * [`final-data-autoconfigure`](final-data/final-data-autoconfigure/README.md)
    * [`final-data-coding`](final-data/final-data-coding/README.md)
    * [`final-data-spring-boot-starter`](final-data/final-data-spring-boot-starter/README.md)
* [`final-json`](final-json/README.md)：定义统一的`Json`操作[`API`](final-json/final-json-core/src/main/java/com/ilikly/finalframework/json/JsonService.java)，支持多种`Json`库，如`jackson`、`fastjson`、`gson`,可自定义`Json`库。
    * [`final-json-core`](final-json/final-json-core/README.md)
    * [`final-json-autoconfigure`](final-json/final-json-autoconfigure/README.md)
    * [`final-json-spring-boot-starter`](final-json/final-json-spring-boot-starter/README.md)
* [`final-redis`](final-redis/README.md)：定义统一的`Redis`操作`API`，支持注解声明式缓存，默认基于`spring-boot-starter-data-redis`实现，可定义义实现。
    * [`final-redis-core`](final-redis/final-redis-core/README.md)
    * [`final-redis-autoconfigure`](final-redis/final-redis-autoconfigure/README.md)
    * [`final-redis-spring-boot-starter`](final-redis/final-redis-spring-boot-starter/README.md)
* [`final-cache`](final-cache/README.md): 基于注解的声明式缓存框架，参考`spring-cache`实现。
    * [`final-cache-core`](final-cache/final-cache-core/README.md)
    * [`final-cache-spring-boot-starter`](final-cache/final-cache-spring-boot-starter/README.md)
* [`final-mybatis`](final-mybatis/README.md)：操作`mysql`的`mybatis`实现，支持多数据源。
    * [`final-mybatis-core`](final-mybatis/final-mybatis-core/README.md)
    * [`final-mybatis-autoconfigure`](final-mybatis/final-mybatis-autoconfigure/README.md)
    * [`final-mybatis-coding`](final-mybatis/final-mybatis-coding/README.md)
    * [`final-mybatis-agent`](final-mybatis/final-mybatis-agent/README.md)
    * [`final-mybatis-spring-boot-starter`](final-mybatis/final-mybatis-spring-boot-starter/README.md)
* [`final-coding`](final-coding/README.md)：注解处理器，在编译时生成模板代码，如`Qentity`、`Mapper`等
* [`final-spring`](final-spring/README.md)：基于`spring-boot`进行二次封装，添加如`HandlerInteceptor`、`ExceptionHandler`等自定义组件，简化`spring`的配置。
    * [`final-spring-core`](final-spring/final-spring-core/README.md)
    * [`final-spring-coding`](final-spring/final-spring-coding/README.md)
    * [`final-spring-boot-starter`](final-spring/final-spring-boot-starter/README.md)
* [`final-util`](final-util/README.md)：定义常用的工具类

