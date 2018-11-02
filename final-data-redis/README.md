# final cache

简单、快速、功能强大的缓存注入框架。

## Get Started

### Import Dependency

### Declaration Cache Annotation

1. `CacheSet`

```java
public class PersonService{
    
    @CacheSet( key = "#{id}", condition = "#{status.code == 1}", expired="1",timeUnit=TimeUnit.DAYS)
    public Person findPersonById(long id){
        return personMapper.findById(id);
    }
}
```

`PersionService#findPersonById(long)`这个方法的的执行过程是，先从缓存中查询`key`为`#{id}`的值，如何缓存中存在，则将值转化为`Person`对象并返回；
如果缓存中不存在，则执行`personMapper.findById(id)`并当`Person.status.code == 1`时将结果设置到缓存中，`key`为`#{id}`，并同时设置`ttl`为`1天`。


**声明式注解**

* `CacheSet`：在方法执行之前查询缓存中与`key`表达式结果对应的值，如果命中，则返回缓存中的数据，否则执行方法，并将符合一定条件`condition`的值设置到缓存中，支持设置`ttl`，实现的是`set`的操作。
* `CacheDel`：在方法执行之后，在满足条件`condition`的前提下，删除缓存中对应的`key`,实现的是`del`的操作。
* `HCahceSet`：与`CacheSet`类似，实现的是`hset`的操作。
* `HCacheDel`：与`CacheDel`类型，实现的是`hdel`的操作。