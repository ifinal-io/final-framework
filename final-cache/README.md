# final cache

# Usage

## import dependency

* maven

```xml
<dependency>
    <groupId>com.ilikly.finalframework</groupId>
    <artifactId>final-cache</artifactId>
</dependency>
```

## declare annotation

* CacheSet

```java
@CacheSet(keyPattern = "person:%s:", keys = "#id", ttl = 1, timeunit = TimeUnit.MINUTES)
public Person findById(Long id){
    return ...;
}
```



