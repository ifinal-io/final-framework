# final-framework

## Modules

### Json

提供统一的`Json`操作`API`，并支持多种`Json`库，如`jackson`、`fastjson`、`gson`。

**Json API**

* `Json.toJson(Object)`：把数据序列化为`json`串。
* `Json.parse(String,Class)`：把`json`串反序列化为`JavaBean`对象。
* `Json.parse(String,Class<? extend Collection>,Class)`：把`json`串反序列化为`List`或`Set`等`Collection`的子类。

**Json Lib**

* `jackson`:
* `fastjson`(未实现):
* `gson`（未实现）:

### Mybatis


### Redis

提供统一的`Redis`操作`API`，并支持声明式注解缓存实现。

**声明式注解**

* `CacheSet`：在方法执行之前查询缓存中与`key`表达式结果对应的值，如果命中，则返回缓存中的数据，否则执行方法，并将符合一定条件`condition`的值设置到缓存中，支持设置`ttl`，实现的是`set`的操作。
* `CacheDel`：在方法执行之后，在满足条件`condition`的前提下，删除缓存中对应的`key`,实现的是`del`的操作。
* `HCahceSet`：与`CacheSet`类似，实现的是`hset`的操作。
* `HCacheDel`：与`CacheDel`类型，实现的是`hdel`的操作。