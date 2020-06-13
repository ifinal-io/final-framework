# final-framework

# QA


## Modules

* [`final-framework`](final-framework/README.md)
    * [`final-core`](final-framework/final-core/README.md)：提供了能用的`interface`，如转换器`Converter`、过滤器`Filter`等。
    * [`final-util`](final-framework/final-util/README.md)：定义常用的工具类
    * [`final-auth`](final-framework/final-auth/README.md)：定义常用的工具类
* [`final-data`](final-data/README.md)：数据对象模型化，为简化数据的CURD做基础。
    * [`final-data-annotation`](final-data/final-data-annotation/README.md)
    * [`final-data-context`](final-data/final-data-context/README.md)
* [`final-coding`](final-coding/README.md): 编译时注解处理器，在编译时生成模板代码
    * [`final-coding-core`](final-coding/final-coding-core/README.md):提供基础的编译时工具类。
    * [`final-coding-entity`](final-coding/final-coding-entity/README.md)：将实现了`IEntity`接口JavaBean类，写入`META-INF/final.entities`，以供其他编译处理器使用。
    * [`final-coding-datasource`](final-coding/final-coding-datasource/README.md): 自动生成`DataSource`配置，结合`spring-auotconfigure`，在要`application`配置文件中给出提示。
    * [`final-coding-query`](final-coding/final-coding-query/README.md)
    * [`final-coding-mapper`](final-coding/final-coding-mapper/README.md)
    * [`final-coding-spring`](final-coding/final-coding-spring/README.md): 提供`SpringFactory`注解，实现将其写入`META-INF/spring.factories`文件，可由`spring`IOC管理。
* [`final-json`](final-json/README.md)：定义统一的`Json`操作[`API`](final-json/final-json-core/src/main/java/com/ilikly/finalframework/json/JsonService.java)，支持多种`Json`库，如`jackson`、`fastjson`、`gson`,可自定义`Json`库。
    * [`final-json-core`](final-json/final-json-core/README.md)
    * [`final-json-autoconfigure`](final-json/final-json-autoconfigure/README.md)
* [`final-redis`](final-redis/README.md)：定义统一的`Redis`操作`API`，支持注解声明式缓存，默认基于`spring-boot-starter-data-redis`实现，可定义义实现。
    * [`final-redis-core`](final-redis/final-redis-core/README.md)
    * [`final-redis-autoconfigure`](final-redis/final-redis-autoconfigure/README.md)
* [`final-cache`](final-cache/README.md): 基于注解的声明式缓存框架，参考`spring-cache`实现。
    * [`final-cache-core`](final-cache/final-cache-core/README.md)
* [`final-mybatis`](final-mybatis/README.md)：操作`mysql`的`mybatis`实现，支持多数据源。
    * [`final-mybatis-core`](final-mybatis/final-mybatis-core/README.md)
    * [`final-mybatis-autoconfigure`](final-mybatis/final-mybatis-autoconfigure/README.md)
* [`final-spring`](final-spring/README.md)：基于`spring-boot`进行二次封装，添加如`HandlerInteceptor`、`ExceptionHandler`等自定义组件，简化`spring`的配置。
    * [`final-spring-context`](final-spring/final-spring-context/README.md)
* [`final-boot`]()
    * [`final-data-spring-boot-starter`](final-boot/final-data-spring-boot-starter/README.md)    
    * [`final-mybatis-spring-boot-starter`](final-boot/final-mybatis-spring-boot-starter/README.md)    
    * [`final-json-spring-boot-starter`](final-boot/final-json-spring-boot-starter/README.md)    
    * [`final-redis-spring-boot-starter`](final-boot/final-redis-spring-boot-starter/README.md)    
    * [`final-cache-spring-boot-starter`](final-boot/final-cache-spring-boot-starter/README.md)    
    * [`final-spring-boot-starter`](final-boot/final-spring-boot-starter/README.md)    
