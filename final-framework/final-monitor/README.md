---
layout: post title: README subtitle: README description: README tags: []
menus:

- README date: 2020-11-11 11:01:14 +800 version: 1.0
---

# final monitor

## 操作日志

通过在方法声明上添加`@MonitorAction`注解，即可快速加入操作日志。

```java

@RestController
public class HelloController {

    @RequestMapping("/hello")
    @MonitorAction("${'访问Hello ' + #word}")
    public String hello(String word) {
        return "hello " + word + "!";
    }

}
```

访问`/hello?word=finalframework`时，可以看到有如下输出：

```verilog
2020-11-11 11:14:09.237  INFO 65380 --- [           main] o.f.monitor.action.ActionLoggerListener  : ==> action handler: {"name":"访问Hello finalframework","type":0,"action":0,"level":3,"levelName":"INFO","levelDesc":"INFO","attributes":{},"trace":"885dee93-8e90-4e61-afa9-b2a3b3bcbf39","timestamp":1605064449179}
```

默认情况下，操作日志仅以日志格式输出到文件中，开发人员可根据需求实现`ActionListener`接口将日志实现持久化等自定义操作。

```java

@FunctionalInterface
public interface ActionListener {

    void onAction(@NonNull Action<?> action);

}
```

> `@MonitorAction`中的大部分属性都支持`SpEL`表达式。
>
> **Note**：老版本中以`{}`表示表达式，新版本中使用`${}`表示表达式。

