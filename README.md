# final-framework

## Modules

* [`final-core`](final-core/README.md)：提供了能用的`interface`，如转换器`Converter`、过滤器`Filter`等。
* [`final-data`](final-data/README.md)：数据对象模型化，为简化数据的CURD做基础。
* [`final-json`](final-json/README.md)：定义统一的`Json`操作[`API`](final-json/src/main/java/cn/com/likly/finalframework/json/JsonService.java)，支持多种`Json`库，如`jackson`、`fastjson`、`gson`,可自定义`Json`库。
* [`final-redis`](final-redis/README.md)：定义统一的`Redis`操作`API`，支持注解声明式缓存，默认基于`spring-boot-starter-data-redis`实现，可定义义实现。
* [`final-mybatis`](final-mybatis/README.md)：操作`mysql`的`mybatis`实现，支持多数据源，
* [`final-coding`](final-coding/README.md)：注解处理器，在编译时生成模板代码，如`Qentity`、`Mapper`等
* [`final-spring`](final-spring/README.md)：基于`spring-boot`进行二次封装，添加如`HandlerInteceptor`、`ExceptionHandler`等自定义组件，简化`spring`的配置。
* [`final-util`](final-util/README.md)：定义常用的工具类

