# final-framework

## Modules

* [`final-context`](final-context)：
* [`final-data`](final-data/README.md):
* [`final-data-json`](final-data-json/README.md)：定义统一的`Json`操作[`API`](final-data-json/src/main/java/cn/com/likly/finalframework/data/json/JsonService.java)，支持多种`Json`库，如`jackson`、`fastjson`、`gson`,可自定义`Json`库。
* [`final-data-redis`](final-data-redis/README.md)：定义统一的`Redos`操作`API`，支持注解声明式缓存，默认基于`spring-boot-starter-data-redis`实现，可定义义实现。
* [`final-data-mybatis`](final-data-mybatis/README.md)：操作`mysql`的`mybatis`实现，支持多数据源，
* [`final-coding`](final-coding/README.md)：注解处理器，在编译时生成模板代码，如`Qentity`、`Mapper`等

