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