# final-framework
final-framework

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
